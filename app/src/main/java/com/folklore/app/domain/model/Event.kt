package com.folklore.app.domain.model

import java.util.Date

data class Event(
    val id: String,
    val title: String,
    val shortDescription: String,
    val description: String,
    val imageUrl: String,
    val website: String,
    val goingCount: Int,
    val likes: Int,
    val startAt: Date,
    val endsAt: Date,
    val location: EventLocation,
    val status: EventStatus,
) {
    val isPopular: Boolean
        get() = goingCount > 50
}

enum class EventStatus {
    ACTIVE,
    INACTIVE,
    UNKNOWN,
}
