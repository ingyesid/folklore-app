package com.folklore.app.presentation.ui.view.events

import com.folklore.app.presentation.model.EventUiModel

data class EventsUiState(
    val popularEvents: List<EventUiModel> = emptyList(),
    val nearEvents: List<EventUiModel> = emptyList(),
    val loading: Boolean = false,
    val error: Boolean = false,
)
