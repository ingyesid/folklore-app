package com.folklore.data.datasource

import com.folklore.data.remote.FolkloreAPI
import com.folklore.data.remote.model.EventDto
import com.folklore.data.remote.model.EventsResponse
import com.folklore.domain.datasource.EventsRemoteDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
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
    @RelaxedMockK
    private lateinit var folkloreAPI: FolkloreAPI

    @RelaxedMockK
    private lateinit var mapperMock: Mapper<EventDto, Event>

    private lateinit var eventsAPIDataSource: EventsRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        eventsAPIDataSource = EventsAPIDataSourceImpl(
            folkloreAPI = folkloreAPI,
            eventDtoMapper = mapperMock,
        )
    }


    @Test
    fun `getEvents() returns a successful result when the API call is successful`() = runTest {
        val responseModelMock = mockk<EventsResponse>(relaxed = true)
        val response = Response.success(responseModelMock)

        coEvery { folkloreAPI.getAllEvents() } returns response

        val result = eventsAPIDataSource.getEvents()

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBeEqualTo responseModelMock.results
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