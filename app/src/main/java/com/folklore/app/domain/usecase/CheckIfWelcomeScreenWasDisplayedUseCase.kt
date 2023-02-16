package com.folklore.app.domain.usecase

import com.folklore.app.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class CheckIfWelcomeScreenWasDisplayedUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {

    suspend operator fun invoke() = userPreferencesRepository.welcomeScreenDisplayed()
}
