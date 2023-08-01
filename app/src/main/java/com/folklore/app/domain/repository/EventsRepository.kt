package com.folklore.app.domain.repository

import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Favorite
import com.folklore.app.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    fun getAllEvents(): Flow<Resource<List<Event>>>
    suspend fun searchEvents(query: String): List<Event>
    fun getEvent(id: String): Flow<Resource<Event>>

    suspend fun updateEvent(event: Event)

    fun getAllFavorites(): Flow<List<Favorite>>

    suspend fun isFavorite(event: Event): Boolean

    suspend fun addToFavorite(event: Event)
    suspend fun removeFromFavorites(event: Event)
}
