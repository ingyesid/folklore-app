package com.folklore.domain.repository

import com.folklore.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun setWelcomeScreenDisplayed(displayed: Boolean)

    suspend fun welcomeScreenDisplayed(): Boolean

    fun getAllUserPreferences(): Flow<UserPreferences>
}
