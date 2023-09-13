package com.folklore.app.presentation.mapper

import com.folklore.app.presentation.model.EventUiModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.utils.ReadableTimeFormatter
import javax.inject.Inject

class EventModelMapper @Inject constructor(
    private val dateFormatter: ReadableTimeFormatter,
) : Mapper<Event, EventUiModel>() {

    override fun mapTo(value: Event): EventUiModel {
        return EventUiModel(
            title = value.title,
            id = value.id,
            goingCount = value.goingCount,
            likes = value.likes,
            imageUrl = value.imageUrl,
            location = "${value.location.city}, ${value.location.state}",
            startDate = dateFormatter.getReadableTime(value.startAt),
        )
    }
}
