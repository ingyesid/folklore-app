package com.folklore.app.presentation.ui.view.events

import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.EventLocation
import com.folklore.app.domain.model.EventStatus
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.usecase.GetAllEventsUseCase
import com.folklore.app.presentation.model.EventUiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date


@OptIn(ExperimentalCoroutinesApi::class)
class EventsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val getEventsUseCase = mockk<GetAllEventsUseCase>()
    private val mapper = mockk<Mapper<Event, EventUiModel>>()
    private lateinit var viewModel: EventsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ui should show events list when use case return success`() = testScope.runTest {
        val events = listOf(
            Event(
                id = "event-12345",
                title = "Kotlin Conference 2023",
                shortDescription = "The biggest Kotlin conference of the year!",
                description = "Join us for three days of Kotlin-related talks, workshops, and networking.",
                imageUrl = "https://www.kotlinconf.com/assets/img/2023/logo.png",
                website = "https://www.kotlinconf.com/",
                goingCount = 1000,
                likes = 500,
                startAt = Date(),
                endsAt = Date(),
                location = EventLocation(
                    city = "San Francisco",
                    state = "CA",
                    latitude = 37.7749,
                    longitude = -122.4194,
                ),
                status = EventStatus.ACTIVE,
            )
        )
        val event = EventUiModel(
            id = "event-12345",
            title = "Kotlin Conference 2023",
            imageUrl = "https://www.kotlinconf.com/assets/img/2023/logo.png",
            goingCount = 1000,
            likes = 500,
            location = "San Francisco, CA",
            startDate = "10 Oct, 2023"
        )
        val uiModels = listOf(
            event
        )

        val successResource = Resource.Success(events)
        every { getEventsUseCase() } returns flowOf(successResource)
        every { mapper.mapTo(any()) } returns event

        viewModel = EventsViewModel(
            dispatcher = testDispatcher,
            getAllEventsUseCase = getEventsUseCase,
            uiModelMapper = mapper,
        )

        val uiState = viewModel.uiState.value

        uiState.popularEvents shouldBeEqualTo uiModels
        uiState.nearEvents.isEmpty().shouldBeTrue()
        uiState.loading.shouldBeFalse()
    }

}