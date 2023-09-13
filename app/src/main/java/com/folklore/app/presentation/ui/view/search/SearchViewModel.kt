package com.folklore.app.presentation.ui.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.presentation.model.SearchResultModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val mapper: Mapper<Event, SearchResultModel>,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState.default())
    val viewState: StateFlow<SearchUiState> = _uiState

    private var queryJob: Job? = null

    fun updateQuery(query: String) {
        queryJob?.cancel()
        _uiState.update { state -> state.copy(searchQuery = query) }
        queryJob = viewModelScope.launch(Dispatchers.IO) {
            val searchResults = searchMoviesUseCase.execute(query)
            _uiState.update { state ->
                state.copy(searchResults = searchResults.map { event ->
                    mapper.mapTo(event)
                })
            }
        }
    }

    fun clearQuery() {
        queryJob?.cancel()
        _uiState.update { state -> state.copy(searchQuery = "", searchResults = emptyList()) }
    }
}

