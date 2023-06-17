package com.folklore.app.presentation.ui.view.home.model

data class EventUiModel(
    val id: String,
    val title: String,
    val shortDescription: String,
    val imageUrl: String,
    val goingCount: Int,
    val likes: Int,
    val location: String,
)
