package com.folklore.data.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.folklore.domain.datasource.UserPreferencesDataSource
import com.folklore.domain.model.SortOrder
import com.folklore.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LocalPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesDataSource {

    override suspend fun setWelcomeScreenDisplayed(displayed: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_WELCOME_SCREEN_DISPLAYED] = displayed
        }
    }

    override suspend fun welcomeScreenDisplayed(): Boolean {
        return dataStore.data.first()[KEY_WELCOME_SCREEN_DISPLAYED] ?: false
    }

    override fun getAllUserPreferences(): Flow<UserPreferences> =
        dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    Log.e("LocalPreferencesDataSource", "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                mapUserPreferences(preferences)
            }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val sortOrder =
            SortOrder.valueOf(
                preferences[KEY_SORT_ORDER] ?: SortOrder.NONE.name,
            )
        val welcomeScreenDisplayed = preferences[KEY_WELCOME_SCREEN_DISPLAYED] ?: false
        val cities = preferences[KEY_CITIES]?.toList() ?: emptyList()
        val states = preferences[KEY_STATES]?.toList() ?: emptyList()
        return UserPreferences(
            welcomeScreenDisplayed = welcomeScreenDisplayed,
            sortOrder = sortOrder,
            cities = cities,
            states = states,
        )
    }

    companion object {
        val KEY_WELCOME_SCREEN_DISPLAYED = booleanPreferencesKey("KEY_WELCOME_SCREEN_DISPLAYED")
        val KEY_SORT_ORDER = stringPreferencesKey("KEY_SORT_ORDER")
        val KEY_CITIES = stringSetPreferencesKey("KEY_CITIES")
        val KEY_STATES = stringSetPreferencesKey("KEY_STATES")
    }
}
