package com.folklore.app.presentation.ui.view.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.di.IoDispatcher
import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Favorite
import com.folklore.domain.usecase.GetAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val uiModelMapper: Mapper<Favorite, FavoriteEventUiModel>,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() = viewModelScope.launch(dispatcher) {
        getAllFavoritesUseCase().collect { allFavorites ->
            _uiState.update {
                it.copy(
                    loading = false,
                    favorites = uiModelMapper.mapCollection(allFavorites)
                )
            }
        }
    }
}
