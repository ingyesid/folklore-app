package com.folklore.app.presentation.ui.view.home

import com.folklore.app.domain.model.Event

data class HomeUiState(
    val searchBoxQuery: String = "",
    val popularEvents: List<Event> = emptyList(),
    val nearEvents: List<Event> = emptyList(),
    val loading: Boolean = false,
)
