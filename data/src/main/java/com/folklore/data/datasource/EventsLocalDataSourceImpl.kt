package com.folklore.data.datasource

import com.folklore.data.database.EventDao
import com.folklore.data.database.model.EventEntity
import com.folklore.domain.datasource.EventsLocalDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class EventsLocalDataSourceImpl @Inject constructor(
    private val eventsDao: EventDao,
    private val entityMapper: Mapper<EventEntity, Event>,
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

    override fun getFavorites(): Flow<List<Event>> = eventsDao.getAllFavorites()
        .mapLatest { it.map { eventEntity -> entityMapper.mapTo(eventEntity) } }


    override suspend fun isFavorite(event: Event) = event.isFavorite

    override suspend fun addToFavorites(event: Event) {
        val favoriteEvent = event.copy(isFavorite = true)
        updateEvent(favoriteEvent)
    }

    override suspend fun removeFromFavorites(event: Event) {
        val favoriteEvent = event.copy(isFavorite = false)
        updateEvent(favoriteEvent)
    }

}
