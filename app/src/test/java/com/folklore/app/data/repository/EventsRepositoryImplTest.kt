package com.folklore.app.data.repository

import com.folklore.app.data.remote.model.EventDto
import com.folklore.app.domain.datasource.EventsLocalDataSource
import com.folklore.app.domain.datasource.EventsRemoteDataSource
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.EventLocation
import com.folklore.app.domain.model.EventStatus
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.repository.EventsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import java.util.Date


@OptIn(ExperimentalCoroutinesApi::class)
internal class EventsRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var localDataSource: EventsLocalDataSource

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: EventsRemoteDataSource

    @MockK(relaxed = true)
    private lateinit var eventDtoMapper: Mapper<EventDto, Event>

    private lateinit var eventsRepository: EventsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        eventsRepository = EventsRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            eventDtoMapper = eventDtoMapper,
        )
    }


    @Test
    fun `getAllEvents return events from local data source`() = runTest {
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

        coEvery { localDataSource.getAllEvents() } returns events

        val result = eventsRepository.getAllEvents()

        result.drop(1).first() shouldBeInstanceOf Resource.Success::class.java
        (result.drop(1).first() as Resource.Success).data shouldBeEqualTo events
    }

    @Test
    fun `getAllEvents return events from local data source and then from remote data source`() =
        runTest {
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
            val newEvents = listOf(
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
                ),
                Event(
                    id = "event-67890",
                    title = "Kotlin Conference 2024",
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

            coEvery { localDataSource.getAllEvents() } returns events

            val result = eventsRepository.getAllEvents()

            result.drop(1).first() shouldBeInstanceOf Resource.Success::class.java
        }
}