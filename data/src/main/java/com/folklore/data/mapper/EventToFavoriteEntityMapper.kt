package com.folklore.data.mapper

import com.folklore.data.database.model.FavoriteEntity
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.utils.ReadableTimeFormatter
import javax.inject.Inject

class EventToFavoriteEntityMapper @Inject constructor(
    private val dateFormatter: ReadableTimeFormatter
) : Mapper<Event, FavoriteEntity>() {

    override fun mapTo(value: Event): FavoriteEntity {
        return FavoriteEntity(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
            location = value.location.city + ", " + value.location.state,
            startDate = dateFormatter.getReadableTime(value.startAt),
        )
    }
}
