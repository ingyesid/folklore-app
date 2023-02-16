package com.folklore.app.domain.datasource

import com.folklore.app.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    suspend fun setWelcomeScreenDisplayed(displayed: Boolean)

    suspend fun welcomeScreenDisplayed(): Boolean

    fun getAllUserPreferences(): Flow<UserPreferences>
}
