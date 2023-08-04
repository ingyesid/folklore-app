package com.folklore.app.data.remote

import com.folklore.app.data.remote.model.EventsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FolkloreAPI {

    @GET("" +
            "classes/Event")
    suspend fun getAllEvents(): Response<EventsResponse>
}
