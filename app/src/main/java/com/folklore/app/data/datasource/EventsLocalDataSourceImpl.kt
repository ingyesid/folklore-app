package com.folklore.app.data.datasource

import com.folklore.app.data.database.EventDao
import com.folklore.app.data.database.FavoriteDao
import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.data.database.model.FavoriteEntity
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Favorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class EventsLocalDataSourceImpl @Inject constructor(
    private val eventsDao: EventDao,
    private val favoritesDao: FavoriteDao,
    private val entityMapper: Mapper<EventEntity, Event>,
    private val favEntityMapper: Mapper<FavoriteEntity, Favorite>,
    private val eventToFavoriteMapper: Mapper<Event, FavoriteEntity>,
) : EventsLocalDataSource {
    override suspend fun getAllEvents(): List<Event> {
        return entityMapper.mapCollection(eventsDao.getAllEvents(""))
    }

    override suspend fun getEvent(id: String): Event? {
        val eventEntity = eventsDao.getEvent(id)
        return try {
            eventEntity?.let { entityMapper.mapTo(it) }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveEvents(events: List<Event>) {
        eventsDao.saveEvents(events.map { event -> entityMapper.reverseTransform(event) })
    }

    override suspend fun updateEvent(event: Event) {
        eventsDao.updateEvent(entityMapper.reverseTransform(event))
    }

    override suspend fun searchEvents(query: String): List<Event> {
        return entityMapper.mapCollection(eventsDao.getAllEvents(query))
    }

    override fun getFavorites(): Flow<List<Favorite>> =
        favoritesDao.getAllFavorites().mapLatest {
            it.map { entity -> favEntityMapper.mapTo(entity) }
        }


    override suspend fun isFavorite(event: Event) = favoritesDao.getFavorite(event.id) != null

    override suspend fun addToFavorites(event: Event) {
        favoritesDao.addFavorite(eventToFavoriteMapper.mapTo(event))
    }

    override suspend fun removeFromFavorites(event: Event) {
        favoritesDao.removeFavorite(event.id)
    }

}
