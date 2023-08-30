package com.folklore.app.data.datasource

import com.folklore.app.data.remote.FolkloreAPI
import com.folklore.app.data.remote.model.EventDto
import com.folklore.domain.datasource.EventsRemoteDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event

class EventsAPIDataSourceImpl(
    private val folkloreAPI: FolkloreAPI,
    private val eventDtoMapper: Mapper<EventDto, Event>,
) : EventsRemoteDataSource {

    override suspend fun getEvents(): Result<List<Event>> {
        return try {
            val apiResponse = folkloreAPI.getAllEvents()
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                Result.success(
                    eventDtoMapper.mapCollection(
                        apiResponse.body()?.results ?: emptyList()
                    )
                )
            } else {
                Result.failure(Exception(apiResponse.errorBody().toString()))
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
