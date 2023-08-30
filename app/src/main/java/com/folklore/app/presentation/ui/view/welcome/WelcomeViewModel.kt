package com.folklore.app.presentation.ui.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.folklore.app.di.IoDispatcher
import com.folklore.domain.usecase.CheckIfWelcomeScreenWasDisplayedUseCase
import com.folklore.domain.usecase.SetWelcomeScreenWasDisplayedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val checkIfWelcomeScreenWasDisplayedUseCase: CheckIfWelcomeScreenWasDisplayedUseCase,
    private val setWelcomeScreenWasDisplayedUseCase: SetWelcomeScreenWasDisplayedUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkIfScreenWasDisplayed()
    }

    private fun checkIfScreenWasDisplayed() = viewModelScope.launch(dispatcher) {
        val screenWasDisplayed = checkIfWelcomeScreenWasDisplayedUseCase()
        _uiState.update {
            it.copy(screenWasAlreadyDisplayed = screenWasDisplayed, fetchingPreferences = false)
        }
    }

    fun markScreenAsDisplayed() = viewModelScope.launch(dispatcher) {
        setWelcomeScreenWasDisplayedUseCase(displayed = true)
        _uiState.update {
            it.copy(screenWasAlreadyDisplayed = true)
        }
    }
}
