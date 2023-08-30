package com.folklore.domain.datasource

import com.folklore.domain.model.Event

interface EventsRemoteDataSource {

    suspend fun getEvents(): Result<List<Event>>
}
