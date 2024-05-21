package com.folklore.app.presentation.ui.view.event

import com.folklore.app.presentation.model.EventDetailsUiModel

data class EventUiState(
    val loading: Boolean = false,
    val isFavorite: Boolean = false,
    val event: EventDetailsUiModel,
    override val isAddToFavoriteAvailable: Boolean = false,
) : BaseEventUiState {
    companion object {
        fun default() = EventUiState(
            loading = false,
            event = EventDetailsUiModel(
                id = "",
                title = "",
                description = "",
                imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                goingCount = 0,
                likes = 0,
                location = "",
                startDate = "",
                endDate = "",
            )
        )
    }
}

