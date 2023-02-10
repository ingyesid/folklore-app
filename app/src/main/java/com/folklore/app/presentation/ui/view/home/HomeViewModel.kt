package com.folklore.app.presentation.ui.view.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun searchEvents(query: String) {
        _uiState.update {
            it.copy(searchBoxQuery = query)
        }
    }
}
