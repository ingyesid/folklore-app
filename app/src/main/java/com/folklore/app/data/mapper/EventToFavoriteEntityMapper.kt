package com.folklore.app.data.mapper

import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.data.database.model.FavoriteEntity
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.EventLocation
import com.folklore.app.domain.model.EventStatus
import com.folklore.app.domain.model.Favorite
import com.folklore.app.domain.utils.ReadableTimeFormatter
import java.util.*
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
