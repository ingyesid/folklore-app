package com.folklore.app.data.mapper

import com.folklore.app.data.remote.model.EventDto
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.model.EventLocation
import com.folklore.domain.model.EventStatus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventDtoMapper @Inject constructor(
    private val dateFormatter: SimpleDateFormat,
) : Mapper<EventDto, Event>() {

    override fun mapTo(value: EventDto): Event {
        return Event(
            title = value.title,
            id = value.objectId,
            shortDescription = value.description,
            description = value.description,
            goingCount = value.going,
            likes = value.likes,
            imageUrl = value.imageUrl,
            website = value.website ?: "",
            startAt = dateFormatter.parse(value.start.iso) ?: Date(),
            endsAt = dateFormatter.parse(value.endsAt.iso) ?: Date(),
            status = mapStatus(value.status),
            location = mapEventLocation(value),
        )
    }

    private fun mapEventLocation(value: EventDto) = EventLocation(
        city = value.city,
        state = value.state,
        latitude = value.location.latitude,
        longitude = value.location.longitude,
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
}
