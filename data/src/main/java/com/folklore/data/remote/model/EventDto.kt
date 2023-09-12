package com.folklore.data.remote.model

data class EventDto(
    val city: String,
    val createdAt: String,
    val description: String,
    val going: Int,
    val imageUrl: String,
    val likes: Int,
    val location: LocationDto,
    val objectId: String,
    val start: DateDto,
    val endsAt: DateDto,
    val state: String,
    val status: String,
    val title: String,
    val updatedAt: String,
    val website: String?,
)
