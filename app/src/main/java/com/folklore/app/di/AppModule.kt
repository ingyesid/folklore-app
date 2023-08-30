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
import com.folklore.app.data.database.FavoriteDao
import com.folklore.app.data.database.FolkloreDatabase
import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.data.database.model.FavoriteEntity
import com.folklore.app.data.datasource.EventsAPIDataSourceImpl
import com.folklore.app.data.datasource.EventsLocalDataSourceImpl
import com.folklore.app.data.mapper.EventDtoMapper
import com.folklore.app.data.mapper.EventEntityMapper
import com.folklore.app.data.mapper.EventToFavoriteEntityMapper
import com.folklore.app.data.mapper.FavoriteEntityMapper
import com.folklore.app.data.remote.AuthHeaderInterceptor
import com.folklore.app.data.remote.FolkloreAPI
import com.folklore.app.data.remote.model.EventDto
import com.folklore.app.data.repository.EventsRepositoryImpl
import com.folklore.app.presentation.mapper.EventDetailsModelMapper
import com.folklore.app.presentation.mapper.EventModelMapper
import com.folklore.app.presentation.mapper.FavoriteUiModelMapper
import com.folklore.app.presentation.mapper.SearchResultModelMapper
import com.folklore.app.presentation.model.EventDetailsUiModel
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.app.presentation.model.SearchResultModel
import com.folklore.app.presentation.utils.DateStringFormatter
import com.folklore.domain.datasource.EventsLocalDataSource
import com.folklore.domain.datasource.EventsRemoteDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.model.Favorite
import com.folklore.domain.repository.EventsRepository
import com.folklore.domain.usecase.AddFavoriteUseCase
import com.folklore.domain.usecase.CheckIfEventIsFavoriteUseCase
import com.folklore.domain.usecase.GetAllEventsUseCase
import com.folklore.domain.usecase.GetAllFavoritesUseCase
import com.folklore.domain.usecase.RemoveFromFavoriteUseCase
import com.folklore.domain.utils.ReadableTimeFormatter
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
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideEventsDAO(database: FolkloreDatabase): EventDao {
        return database.eventDao
    }

    @Provides
    @Singleton
    fun provideFavoritesDAO(database: FolkloreDatabase): FavoriteDao {
        return database.favoriteDao
    }

    @Provides
    @Singleton
    fun provideEntityMapper(): Mapper<EventEntity, Event> {
        return EventEntityMapper()
    }

    @Provides
    @Singleton
    fun provideFavoriteEntityMapper(): Mapper<FavoriteEntity, Favorite> {
        return FavoriteEntityMapper()
    }

    @Provides
    @Singleton
    fun provideEventToFavoriteEntityMapper(dateFormatter: ReadableTimeFormatter): Mapper<Event, FavoriteEntity> {
        return EventToFavoriteEntityMapper(dateFormatter)
    }

    @Provides
    @Singleton
    fun provideEventDtoMapper(): Mapper<EventDto, Event> {
        return EventDtoMapper(SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US))
    }

    @Provides
    @Singleton
    fun provideEventMapper(formatter: ReadableTimeFormatter): Mapper<Event, EventUiModel> {
        return EventModelMapper(formatter)
    }

    @Provides
    @Singleton
    fun provideSearchResultsMapper(): Mapper<Event, SearchResultModel> {
        return SearchResultModelMapper()
    }

    @Provides
    @Singleton
    fun provideFavoritesUiModelMapper(): Mapper<Favorite, FavoriteEventUiModel> {
        return FavoriteUiModelMapper()
    }

    @Provides
    @Singleton
    fun provideEventDetailsMapper(formatter: ReadableTimeFormatter): Mapper<Event, EventDetailsUiModel> {
        return EventDetailsModelMapper(formatter)
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
        favoritesDao: FavoriteDao,
        favoriteEntityMapper: Mapper<FavoriteEntity, Favorite>,
        eventToFavoriteMapper: Mapper<Event, FavoriteEntity>,
    ): EventsLocalDataSource {
        return EventsLocalDataSourceImpl(
            eventsDao = dao,
            entityMapper = mapper,
            favoritesDao = favoritesDao,
            favEntityMapper = favoriteEntityMapper,
            eventToFavoriteMapper = eventToFavoriteMapper
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
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().callTimeout(45L, TimeUnit.MINUTES)
        .connectTimeout(45L, TimeUnit.MINUTES).readTimeout(45L, TimeUnit.MINUTES)
        .addInterceptor(AuthHeaderInterceptor()).addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        ).build()

}
