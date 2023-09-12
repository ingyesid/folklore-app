package com.folklore.app.di

import android.content.Context
import com.folklore.app.presentation.mapper.EventDetailsModelMapper
import com.folklore.app.presentation.mapper.EventModelMapper
import com.folklore.app.presentation.mapper.FavoriteUiModelMapper
import com.folklore.app.presentation.mapper.SearchResultModelMapper
import com.folklore.app.presentation.model.EventDetailsUiModel
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.app.presentation.model.SearchResultModel
import com.folklore.app.presentation.utils.DateStringFormatter
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.model.Favorite
import com.folklore.domain.utils.ReadableTimeFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}