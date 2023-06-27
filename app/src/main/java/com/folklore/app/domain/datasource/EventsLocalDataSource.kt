package com.folklore.app.domain.datasource

import com.folklore.app.domain.model.Event

interface EventsLocalDataSource {

    suspend fun getAllEvents(): List<Event>

    suspend fun searchEvents(query: String): List<Event>

    suspend fun getEvent(id: String): Event

    suspend fun saveEvents(events: List<Event>)

    suspend fun updateEvent(event: Event)
}
