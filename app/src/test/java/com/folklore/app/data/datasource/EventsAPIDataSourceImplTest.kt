package com.folklore.app.data.datasource

import com.folklore.app.data.remote.FolkloreAPI
import com.folklore.app.data.remote.model.EventsResponse
import com.folklore.app.domain.datasource.EventsRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventsAPIDataSourceImplTest {
    @MockK
    private lateinit var folkloreAPI: FolkloreAPI

    private lateinit var eventsAPIDataSource: EventsRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        eventsAPIDataSource = EventsAPIDataSourceImpl(
            folkloreAPI = folkloreAPI,
        )
    }


    @Test
    fun `getEvents() returns a successful result when the API call is successful`() = runTest {
        val responseModelMock = mockk<EventsResponse>(relaxed = true)
        val response = Response.success(responseModelMock)

        coEvery { folkloreAPI.getAllEvents() } returns response

        val result = eventsAPIDataSource.getEvents()

        result.isSuccess shouldBe true
        result.getOrNull()!!.results shouldBeEqualTo responseModelMock.results
    }

    @Test
    fun `getEvents() returns a failure result when the API call fails`() = runTest {
        val exception = Exception("Something went wrong")
        coEvery { folkloreAPI.getAllEvents() } throws exception

        val result = eventsAPIDataSource.getEvents()

        result.isFailure shouldBeEqualTo true
        result.exceptionOrNull() shouldBeEqualTo exception
    }
}