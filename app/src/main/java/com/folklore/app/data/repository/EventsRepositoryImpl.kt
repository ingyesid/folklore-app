package com.folklore.app.data.repository

import com.folklore.app.data.remote.model.EventDto
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.datasource.EventsRemoteDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
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
        val cachedEvents = localDataSource.getEvents()
        if (cachedEvents.isNotEmpty()) {
            emit(Resource.Success(cachedEvents))
        }
        val remoteResult = remoteDataSource.getEvents()
        if (remoteResult.isSuccess) {
            val events = remoteResult.getOrNull()?.results ?: emptyList()
            localDataSource.saveEvents(eventDtoMapper.mapCollection(events))
            emit(Resource.Success(localDataSource.getEvents()))
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

    override suspend fun updateEvent(event: Event) {
        localDataSource.updateEvent(event)
    }
}
