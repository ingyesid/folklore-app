package com.folklore.app.presentation.ui.view.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.usecase.GetAllEventsUseCase
import com.folklore.app.presentation.mapper.EventModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val uiModelMapper: EventModelMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch {
        getAllEventsUseCase().collect { resource ->
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
                            popularEvents = resource.data.filter { event ->
                                event.isPopular
                            }.map { event ->
                                uiModelMapper.mapTo(event)
                            },
                            nearEvents = resource.data.filter { event ->
                                !event.isPopular
                            }.map { event ->
                                uiModelMapper.mapTo(event)
                            },
                        )
                    }
                }
            }
        }
    }

}
