package com.folklore.app.presentation.ui.view.home

import com.folklore.app.presentation.model.EventModel

data class HomeUiState(
    val searchBoxQuery: String = "",
    val popularEvents: List<EventModel> = emptyList(),
    val nearEvents: List<EventModel> = emptyList(),
    val loading: Boolean = false,
)
