package com.folklore.app.presentation.model

data class EventDetailsUiModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val goingCount: Int,
    val likes: Int,
    val location: String,
    val startDate: String,
    val endDate: String,
    val website: String = "",
)
