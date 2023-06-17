package com.folklore.app.domain.repository

import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    fun getAllEvents(): Flow<Resource<List<Event>>>

    suspend fun updateEvent(event: Event)
}
