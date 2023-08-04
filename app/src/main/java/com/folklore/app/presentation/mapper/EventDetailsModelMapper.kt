package com.folklore.app.presentation.mapper

import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.utils.ReadableTimeFormatter
import com.folklore.app.presentation.model.EventDetailsUiModel
import com.folklore.app.presentation.model.EventUiModel
import javax.inject.Inject

class EventDetailsModelMapper @Inject constructor(
    private val dateFormatter: ReadableTimeFormatter,
) : Mapper<Event, EventDetailsUiModel>() {

    override fun mapTo(value: Event): EventDetailsUiModel {
        return EventDetailsUiModel(
            title = value.title,
            id = value.id,
            description = value.description,
            goingCount = value.goingCount,
            likes = value.likes,
            imageUrl = value.imageUrl,
            location = "${value.location.city}, ${value.location.state}",
            startDate = dateFormatter.getReadableTime(value.startAt),
            endDate = dateFormatter.getReadableTime(value.endsAt),
            website = value.website,
        )
    }
}
