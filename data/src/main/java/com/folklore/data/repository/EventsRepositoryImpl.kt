package com.folklore.data.repository

import com.folklore.domain.datasource.EventsLocalDataSource
import com.folklore.domain.datasource.EventsRemoteDataSource
import com.folklore.domain.model.Event
import com.folklore.domain.model.Resource
import com.folklore.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val localDataSource: EventsLocalDataSource,
    private val remoteDataSource: EventsRemoteDataSource,
) : EventsRepository {

    override fun getAllEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        val cachedEvents = localDataSource.getAllEvents()
        if (cachedEvents.isNotEmpty()) {
            emit(Resource.Success(cachedEvents))
        }
        val remoteResult = remoteDataSource.getEvents()
        if (remoteResult.isSuccess) {
            val events = remoteResult.getOrNull() ?: emptyList()
            localDataSource.saveEvents(events)
            emit(Resource.Success(localDataSource.getAllEvents()))
        } else {
            if (cachedEvents.isEmpty()) {
                emit(
                    Resource.Error(
                        message = remoteResult.exceptionOrNull()?.message
                            ?: "An unknown error occurred",
                    ),
                )
            }
        }
    }

    override suspend fun searchEvents(query: String): List<Event> {
        return localDataSource.searchEvents(query)
    }

    override fun getEvent(id: String): Flow<Resource<Event>> = flow {
        emit(Resource.Loading())
        val event = localDataSource.getEvent(id)
        if (event != null) {
            emit(Resource.Success(event))
        } else {
            emit(Resource.Error("Not found"))
        }
    }

    override suspend fun updateEvent(event: Event) {
        localDataSource.updateEvent(event)
    }

    override fun getAllFavorites(): Flow<List<Event>> {
        return localDataSource.getFavorites()
    }

    override suspend fun isFavorite(event: Event) = localDataSource.isFavorite(event)

    override suspend fun addToFavorite(event: Event){
        return localDataSource.addToFavorites(event)
    }

    override suspend fun removeFromFavorites(event: Event){
        return localDataSource.removeFromFavorites(event)
    }

}
