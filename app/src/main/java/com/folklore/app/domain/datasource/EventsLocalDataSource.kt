package com.folklore.app.domain.datasource

import com.folklore.app.domain.model.Event

interface EventsLocalDataSource {

    suspend fun getEvents(): List<Event>
    suspend fun getEvent(id: String): Event

    suspend fun saveEvents(events: List<Event>)

    suspend fun updateEvent(event: Event)
}
