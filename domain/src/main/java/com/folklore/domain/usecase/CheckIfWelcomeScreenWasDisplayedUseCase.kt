package com.folklore.domain.usecase

import com.folklore.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class CheckIfWelcomeScreenWasDisplayedUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {

    suspend operator fun invoke() = userPreferencesRepository.welcomeScreenDisplayed()
}
