package com.folklore.app.presentation.ui.view.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.domain.model.Resource
import com.folklore.app.domain.usecase.GetAllEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllEvents()
    }

    fun getAllEvents() = viewModelScope.launch {
        getAllEventsUseCase().collect { resource ->
            Log.d("HomeViewModel", "getAllEvents: $resource")
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
                            },
                            nearEvents = resource.data.filter { event ->
                                !event.isPopular
                            },
                        )
                    }
                }
            }
        }
    }

    fun searchEvents(query: String) {
        _uiState.update {
            it.copy(searchBoxQuery = query)
        }
    }
}
