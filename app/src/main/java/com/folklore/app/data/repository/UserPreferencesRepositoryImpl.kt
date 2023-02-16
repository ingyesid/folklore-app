package com.folklore.app.data.repository

import com.folklore.app.domain.datasource.UserPreferencesDataSource
import com.folklore.app.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val localDataSource: UserPreferencesDataSource,
) : UserPreferencesRepository {
    override suspend fun setWelcomeScreenDisplayed(displayed: Boolean) {
        localDataSource.setWelcomeScreenDisplayed(displayed)
    }

    override suspend fun welcomeScreenDisplayed() = localDataSource.welcomeScreenDisplayed()

    override fun getAllUserPreferences() = localDataSource.getAllUserPreferences()
}
