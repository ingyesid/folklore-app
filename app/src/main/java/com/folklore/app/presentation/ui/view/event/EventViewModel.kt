package com.folklore.app.presentation.ui.view.event

import androidx.lifecycle.ViewModel
import com.folklore.app.domain.usecase.GetAllEventsUseCase
import com.folklore.app.presentation.mapper.EventMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val uiModelMapper: EventMapper,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState.default())
    val uiState: StateFlow<EventUiState> = _uiState
}
