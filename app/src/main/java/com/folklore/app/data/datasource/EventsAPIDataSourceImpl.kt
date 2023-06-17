package com.folklore.app.data.datasource

import com.folklore.app.data.remote.FolkloreAPI
import com.folklore.app.data.remote.model.EventsResponse
import com.folklore.app.domain.datasource.EventsRemoteDataSource

class EventsAPIDataSourceImpl(
    private val folkloreAPI: FolkloreAPI,
) : EventsRemoteDataSource {

    override suspend fun getEvents(): Result<EventsResponse> {
        return try {
            val apiResponse = folkloreAPI.getAllEvents()
            if (apiResponse.isSuccessful) {
                Result.success(apiResponse.body()!!)
            } else {
                Result.failure(Exception(apiResponse.errorBody().toString()))
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
