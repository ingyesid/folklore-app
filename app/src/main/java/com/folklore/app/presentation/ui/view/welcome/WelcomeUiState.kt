package com.folklore.app.presentation.ui.view.welcome

data class WelcomeUiState(
    val screenWasAlreadyDisplayed: Boolean = false,
    val fetchingPreferences: Boolean = true,
)
