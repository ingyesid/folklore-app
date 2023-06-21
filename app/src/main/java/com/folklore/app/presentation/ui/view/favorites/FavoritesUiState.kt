package com.folklore.app.presentation.ui.view.favorites

import com.folklore.app.presentation.model.FavoriteEventUiModel

data class FavoritesUiState(
    val loading: Boolean = false,
    val favorites: List<FavoriteEventUiModel> = emptyList(),
)
