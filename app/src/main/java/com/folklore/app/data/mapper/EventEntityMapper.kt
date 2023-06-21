package com.folklore.app.data.mapper

import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.EventLocation
import com.folklore.app.domain.model.EventStatus
import java.util.*
import javax.inject.Inject

class EventEntityMapper @Inject constructor() : Mapper<EventEntity, Event>() {

    override fun mapTo(value: EventEntity): Event {
        return Event(
            title = value.title,
            id = value.id,
            shortDescription = value.shortDescription,
            description = value.description,
            goingCount = value.goingCount,
            likes = value.likes,
            imageUrl = value.imageUrl,
            website = value.website,
            startAt = Date(value.startAt),
            endsAt = Date(value.endsAt),
            status = mapStatus(value.status),
            location = mapEventLocation(value),
        )
    }

    override fun reverseTransform(value: Event): EventEntity {
        return EventEntity(
            title = value.title,
            id = value.id,
            shortDescription = value.shortDescription,
            description = value.description,
            goingCount = value.goingCount,
            likes = value.likes,
            imageUrl = value.imageUrl,
            website = value.website,
            startAt = value.startAt.time,
            endsAt = value.endsAt.time,
            city = value.location.city,
            state = value.location.state,
            latitude = value.location.latitude,
            longitude = value.location.longitude,
            status = reverseMapStatus(value.status),
        )
    }

    private fun mapEventLocation(value: EventEntity) = EventLocation(
        city = value.city,
        state = value.state,
        latitude = value.latitude,
        longitude = value.longitude,
    )

    private fun mapStatus(status: String): EventStatus {
        return when (status) {
            "active" -> {
                EventStatus.ACTIVE
            }
            "inactive" -> {
                EventStatus.INACTIVE
            }
            else -> {
                EventStatus.UNKNOWN
            }
        }
    }

    private fun reverseMapStatus(status: EventStatus): String {
        return when (status) {
            EventStatus.ACTIVE -> {
                "active"
            }
            EventStatus.INACTIVE -> {
                "inactive"
            }
            else -> {
                "other"
            }
        }
    }
}