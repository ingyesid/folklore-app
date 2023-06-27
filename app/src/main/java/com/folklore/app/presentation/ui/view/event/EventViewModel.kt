package com.folklore.app.presentation.ui.view.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.usecase.GetEventByIdUseCase
import com.folklore.app.presentation.mapper.EventDetailsModelMapper
import com.folklore.app.presentation.mapper.EventModelMapper
import com.folklore.app.presentation.model.EventDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val uiModelMapper: Mapper<Event, EventDetailsUiModel>,
) : ViewModel() {

    private val eventId: String = checkNotNull(
        savedStateHandle["eventId"],
    )

    private val _uiState = MutableStateFlow(EventUiState.default())
    val uiState: StateFlow<EventUiState> = _uiState


    init {
        loadEvent()
    }

    private fun loadEvent() {
        viewModelScope.launch {
            getEventByIdUseCase(eventId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(loading = false) }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(loading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                event = uiModelMapper.mapTo(resource.data)
                            )
                        }
                    }
                }
            }
        }
    }
}
