package com.folklore.app.data.repository

import com.folklore.app.data.remote.model.EventDto
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.datasource.EventsRemoteDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Favorite
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val localDataSource: EventsLocalDataSource,
    private val remoteDataSource: EventsRemoteDataSource,
    private val eventDtoMapper: Mapper<EventDto, Event>,
) : EventsRepository {

    override fun getAllEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        val cachedEvents = localDataSource.getAllEvents()
        if (cachedEvents.isNotEmpty()) {
            emit(Resource.Success(cachedEvents))
        }
        val remoteResult = remoteDataSource.getEvents()
        if (remoteResult.isSuccess) {
            val events = remoteResult.getOrNull()?.results ?: emptyList()
            localDataSource.saveEvents(eventDtoMapper.mapCollection(events))
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

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return localDataSource.getFavorites()
    }
}
