package com.folklore.app.presentation.mapper


import com.folklore.app.presentation.model.EventDetailsUiModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.utils.ReadableTimeFormatter
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
