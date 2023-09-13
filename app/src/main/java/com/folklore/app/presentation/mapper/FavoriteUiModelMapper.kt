package com.folklore.app.presentation.mapper

import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.utils.ReadableTimeFormatter
import javax.inject.Inject

class FavoriteUiModelMapper @Inject constructor(
    private val dateFormatter: ReadableTimeFormatter,
) : Mapper<Event, FavoriteEventUiModel>() {

    override fun mapTo(value: Event): FavoriteEventUiModel {
        return FavoriteEventUiModel(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
            location = value.location.city + " , " + value.location.state,
            startDate = dateFormatter.getReadableTime(value.startAt),
        )
    }
}
