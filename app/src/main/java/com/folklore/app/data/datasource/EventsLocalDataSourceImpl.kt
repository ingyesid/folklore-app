package com.folklore.app.data.datasource

import android.util.Log
import com.folklore.app.data.database.EventDao
import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import javax.inject.Inject

class EventsLocalDataSourceImpl @Inject constructor(
    private val eventsDao: EventDao,
    private val entityMapper: Mapper<EventEntity, Event>,
) : EventsLocalDataSource {
    override suspend fun getEvents(): List<Event> {
        return entityMapper.mapCollection(eventsDao.getAllEvents(""))
    }

    override suspend fun saveEvents(events: List<Event>) {
        Log.d("EventsLocalDataSource", "saveEvents: $events")
        eventsDao.saveEvents(events.map { event -> entityMapper.reverseTransform(event) })
    }

    override suspend fun updateEvent(event: Event) {
        eventsDao.updateEvent(entityMapper.reverseTransform(event))
    }
}
