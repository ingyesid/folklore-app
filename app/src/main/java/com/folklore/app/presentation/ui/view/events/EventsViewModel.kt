package com.folklore.app.presentation.ui.view.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.di.IoDispatcher
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import com.folklore.domain.model.Resource
import com.folklore.domain.usecase.GetAllEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val uiModelMapper: Mapper<Event, EventUiModel>,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(loading = true) }
        getAllEventsUseCase().collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = resource.exception?.message ?: resource.message
                        )
                    }
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
