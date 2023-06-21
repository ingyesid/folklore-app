package com.folklore.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.folklore.app.BuildConfig
import com.folklore.app.data.database.EventDao
import com.folklore.app.data.database.FolkloreDatabase
import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.data.datasource.EventsAPIDataSourceImpl
import com.folklore.app.data.datasource.EventsLocalDataSourceImpl
import com.folklore.app.data.mapper.EventDtoMapper
import com.folklore.app.data.mapper.EventEntityMapper
import com.folklore.app.data.remote.AuthHeaderInterceptor
import com.folklore.app.data.remote.FolkloreAPI
import com.folklore.app.data.remote.model.EventDto
import com.folklore.app.data.repository.EventsRepositoryImpl
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.datasource.EventsRemoteDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.repository.EventsRepository
import com.folklore.app.domain.usecase.GetAllEventsUseCase
import com.folklore.app.domain.utils.ReadableTimeFormatter
import com.folklore.app.presentation.mapper.EventMapper
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.utils.DateStringFormatter
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
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideEventsDAO(database: FolkloreDatabase): EventDao {
        return database.dao
    }

    @Provides
    @Singleton
    fun provideEntityMapper(): Mapper<EventEntity, Event> {
        return EventEntityMapper()
    }

    @Provides
    @Singleton
    fun provideEventDtoMapper(): Mapper<EventDto, Event> {
        return EventDtoMapper(SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US))
    }

    @Provides
    @Singleton
    fun provideEventMapper(formatter: ReadableTimeFormatter): Mapper<Event, EventUiModel> {
        return EventMapper(formatter)
    }

    @Provides
    @Singleton
    fun provideReadableTimeFormatter(@ApplicationContext context: Context): ReadableTimeFormatter {
        return DateStringFormatter(context)
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
    fun provideEventsRemoteDataSource(api: FolkloreAPI): EventsRemoteDataSource {
        return EventsAPIDataSourceImpl(
            folkloreAPI = api,
        )
    }

    @Provides
    @Singleton
    fun provideEventsRepository(
        localDataSource: EventsLocalDataSource,
        remoteDataSource: EventsRemoteDataSource,
        eventDtoMapper: Mapper<EventDto, Event>,
    ): EventsRepository {
        return EventsRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            eventDtoMapper = eventDtoMapper,
        )
    }

    @Provides
    @Singleton
    fun provideGetAllEventsUseCase(repository: EventsRepository): GetAllEventsUseCase {
        return GetAllEventsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFolkloreAPI(okHttpClient: OkHttpClient): FolkloreAPI {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(45L, TimeUnit.MINUTES)
            .connectTimeout(45L, TimeUnit.MINUTES)
            .readTimeout(45L, TimeUnit.MINUTES)
            .addInterceptor(AuthHeaderInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).build()
}
