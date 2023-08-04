package com.folklore.app.presentation.ui.view.search

import com.folklore.app.presentation.model.SearchResultModel

data class SearchUiState(
    val searchQuery: String,
    val searchResults: List<SearchResultModel>,
) {

    companion object {
        fun default() = SearchUiState(
            searchQuery = "",
            searchResults = emptyList(),
        )
    }
}
