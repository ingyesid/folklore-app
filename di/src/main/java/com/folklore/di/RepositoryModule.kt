package com.folklore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.folklore.data.database.EventDao
import com.folklore.data.database.FolkloreDatabase
import com.folklore.data.database.model.EventEntity
import com.folklore.data.datasource.EventsAPIDataSourceImpl
import com.folklore.data.datasource.EventsLocalDataSourceImpl
import com.folklore.data.mapper.EventDtoMapper
import com.folklore.data.mapper.EventEntityMapper
import com.folklore.data.remote.AuthHeaderInterceptor
import com.folklore.data.remote.FolkloreAPI
import com.folklore.data.remote.model.EventDto
import com.folklore.data.repository.EventsRepositoryImpl
import com.folklore.domain.datasource.EventsLocalDataSource
import com.folklore.domain.datasource.EventsRemoteDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.repository.EventsRepository
import com.folklore.domain.usecase.AddFavoriteUseCase
import com.folklore.domain.usecase.CheckIfEventIsFavoriteUseCase
import com.folklore.domain.usecase.GetAllEventsUseCase
import com.folklore.domain.usecase.GetAllFavoritesUseCase
import com.folklore.domain.usecase.RemoveFromFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    private const val USER_PREFERENCES_FILE = "preferences"
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:s"

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES_FILE) },
        )
    }

    @Provides
    @Singleton
    fun provideEventsDatabase(@ApplicationContext context: Context): FolkloreDatabase {
        return Room.databaseBuilder(
            context,
            FolkloreDatabase::class.java,
            "folklore.db",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideEventsDAO(database: FolkloreDatabase): EventDao {
        return database.eventDao
    }


    @Provides
    @Singleton
    fun provideEventDtoMapper(): Mapper<EventDto, Event> {
        return EventDtoMapper(SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US))
    }

    @Provides
    @Singleton
    fun provideEntityMapper(): Mapper<EventEntity, Event> {
        return EventEntityMapper()
    }

    @Provides
    @Singleton
    fun provideEventsLocalDataSource(
        dao: EventDao,
        mapper: Mapper<EventEntity, Event>,
    ): EventsLocalDataSource {
        return EventsLocalDataSourceImpl(
            eventsDao = dao,
            entityMapper = mapper,
        )
    }

    @Provides
    @Singleton
    fun provideEventsRemoteDataSource(
        api: FolkloreAPI,
        eventDtoMapper: Mapper<EventDto, Event>,
    ): EventsRemoteDataSource {
        return EventsAPIDataSourceImpl(
            folkloreAPI = api,
            eventDtoMapper = eventDtoMapper
        )
    }

    @Provides
    @Singleton
    fun provideEventsRepository(
        localDataSource: EventsLocalDataSource,
        remoteDataSource: EventsRemoteDataSource,
    ): EventsRepository {
        return EventsRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideGetAllEventsUseCase(repository: EventsRepository): GetAllEventsUseCase {
        return GetAllEventsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoritesUseCase(repository: EventsRepository): GetAllFavoritesUseCase {
        return GetAllFavoritesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckIfEventIsFavoriteUseCase(repository: EventsRepository): CheckIfEventIsFavoriteUseCase {
        return CheckIfEventIsFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddFavoriteUseCase(repository: EventsRepository): AddFavoriteUseCase {
        return AddFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromFavoriteUseCase(repository: EventsRepository): RemoveFromFavoriteUseCase {
        return RemoveFromFavoriteUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideFolkloreAPI(okHttpClient: OkHttpClient): FolkloreAPI {
        return Retrofit.Builder().baseUrl(BuildConfig.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient).build()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(45L, TimeUnit.MINUTES)
        .connectTimeout(45L, TimeUnit.MINUTES)
        .readTimeout(45L, TimeUnit.MINUTES)
        .addInterceptor(AuthHeaderInterceptor()).addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        ).build()

}
