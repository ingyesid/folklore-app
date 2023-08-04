package com.folklore.app.presentation.model

data class EventUiModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val goingCount: Int,
    val likes: Int,
    val location: String,
    val startDate: String,
)
