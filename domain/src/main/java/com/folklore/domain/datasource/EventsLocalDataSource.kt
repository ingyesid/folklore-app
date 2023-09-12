package com.folklore.domain.datasource

import com.folklore.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventsLocalDataSource {

    suspend fun getAllEvents(): List<Event>

    suspend fun searchEvents(query: String): List<Event>

    suspend fun getEvent(id: String): Event?

    suspend fun saveEvents(events: List<Event>)

    suspend fun updateEvent(event: Event)

    fun getFavorites(): Flow<List<Event>>

    suspend fun isFavorite(event: Event): Boolean

    suspend fun addToFavorites(event: Event)

    suspend fun removeFromFavorites(event: Event)
}
