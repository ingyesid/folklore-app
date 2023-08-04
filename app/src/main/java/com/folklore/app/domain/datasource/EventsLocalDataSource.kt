package com.folklore.app.domain.datasource

import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface EventsLocalDataSource {

    suspend fun getAllEvents(): List<Event>

    suspend fun searchEvents(query: String): List<Event>

    suspend fun getEvent(id: String): Event?

    suspend fun saveEvents(events: List<Event>)

    suspend fun updateEvent(event: Event)

    fun getFavorites(): Flow<List<Favorite>>
    suspend fun isFavorite(event: Event): Boolean
    suspend fun addToFavorites(event: Event)
    suspend fun removeFromFavorites(event: Event)
}
