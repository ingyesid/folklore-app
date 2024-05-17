package com.folklore.data.datasource


import com.folklore.data.database.EventDao
import com.folklore.data.database.model.EventEntity
import com.folklore.domain.datasource.EventsLocalDataSource
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.model.EventLocation
import com.folklore.domain.model.EventStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.util.Date

class EventsLocalDataSourceImplTest {


    private val eventsDao: EventDao = mockk()

    private val entityMapper: Mapper<EventEntity, Event> = mockk(relaxed = true)

    private lateinit var eventsLocalDataSource: EventsLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        eventsLocalDataSource = EventsLocalDataSourceImpl(
            eventsDao = eventsDao,
            entityMapper = entityMapper,
        )
    }


    @Test
    fun `getAllEvents return events list`() = runTest {
        val eventsEntities = listOf(
            EventEntity(
                id = "event-12345",
                title = "Kotlin Conference 2023",
                shortDescription = "The biggest Kotlin conference of the year!",
                description = "Join us for three days of Kotlin-related talks, workshops, and networking.",
                imageUrl = "https://www.kotlinconf.com/assets/img/2023/logo.png",
                website = "https://www.kotlinconf.com/",
                goingCount = 1000,
                likes = 500,
                startAt = 1657840000,
                endsAt = 1658104000,
                city = "San Francisco",
                state = "CA",
                status = "active",
                latitude = 37.7749,
                longitude = -122.4194,
                isFavorite = false,
            ),
        )

        val events = entityMapper.mapCollection(eventsEntities)

        coEvery { eventsDao.getAllEvents("") } returns eventsEntities

        val result = eventsLocalDataSource.getAllEvents()

        result shouldBeEqualTo events
    }

    @Test
    fun `getEvent return event when dao found`() = runTest {
        val eventEntity = EventEntity(
            id = "event-12345",
            title = "Kotlin Conference 2023",
            shortDescription = "The biggest Kotlin conference of the year!",
            description = "Join us for three days of Kotlin-related talks, workshops, and networking.",
            imageUrl = "https://www.kotlinconf.com/assets/img/2023/logo.png",
            website = "https://www.kotlinconf.com/",
            goingCount = 1000,
            likes = 500,
            startAt = 1657840000,
            endsAt = 1658104000,
            city = "San Francisco",
            state = "CA",
            status = "active",
            latitude = 37.7749,
            longitude = -122.4194,
            isFavorite = false,
        )
        coEvery { eventsDao.getEvent(any()) } returns eventEntity
        every { entityMapper.mapTo(any()) } returns Event(
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

        val result = eventsLocalDataSource.getEvent("event-12345")
        val expected: Event = entityMapper.mapTo(eventEntity)

        result shouldBeEqualTo expected
    }

    @Test
    fun `getEvent return null when dao returns null`() = runTest {
        coEvery { eventsDao.getEvent(any()) } returns null
        val result = eventsLocalDataSource.getEvent("event-12345")

        result shouldBe null
    }
}