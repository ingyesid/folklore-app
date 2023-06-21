package com.folklore.app.presentation.ui.view.event

import com.folklore.app.presentation.model.EventDetailsUiModel

data class EventUiState(
    val loading: Boolean = false,
    val event: EventDetailsUiModel,
){
    companion object{
        fun default() = EventUiState(
            loading = false,
            event = EventDetailsUiModel(
                id = "xyz",
                title = "Google i/o extended",
                description = "lorem ipsum bla",
                imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                goingCount = 20,
                likes = 30,
                location = "Corozal, Sucre",
                startDate = "May 29 , 2034",
            )
        )
    }
}

