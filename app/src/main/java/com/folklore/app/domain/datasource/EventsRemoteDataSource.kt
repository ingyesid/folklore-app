package com.folklore.app.domain.datasource

import com.folklore.app.data.remote.model.EventsResponse

interface EventsRemoteDataSource {

    suspend fun getEvents(): Result<EventsResponse>
}
