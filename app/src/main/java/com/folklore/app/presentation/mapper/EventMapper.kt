package com.folklore.app.presentation.mapper

import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.utils.ReadableTimeFormatter
import com.folklore.app.presentation.model.EventUiModel
import java.util.*
import javax.inject.Inject

class EventMapper @Inject constructor(
    private val dateFormatter: ReadableTimeFormatter,
) : Mapper<Event, EventUiModel>() {

    override fun mapTo(value: Event): EventUiModel {
        return EventUiModel(
            title = value.title,
            id = value.id,
            shortDescription = value.shortDescription,
            goingCount = value.goingCount,
            likes = value.likes,
            imageUrl = value.imageUrl,
            location = "${value.location.city}, ${value.location.state}",
            startDate = dateFormatter.getReadableTime(value.startAt),
        )
    }
}
