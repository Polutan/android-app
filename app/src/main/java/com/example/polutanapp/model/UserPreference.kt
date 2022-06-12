package com.example.polutanapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.polutanapp.response.SavedUser
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow


class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[TOKEN_KEY] ?: "",
                preferences[STATUS_KEY] ?: 0,
                preferences[MESSAGE_KEY] ?: "",
                preferences[SCORE_KEY] ?: 0
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[STATUS_KEY] = user.status
            preferences[MESSAGE_KEY] = user.message
            preferences[SCORE_KEY] = user.score
        }
    }

    suspend fun savedScoreAQI(savedUser: SavedUser) {
        dataStore.edit { preferences ->
            preferences[SCORE_KEY] = savedUser.score
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATUS_KEY] = 200
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATUS_KEY] = 400
            preferences[TOKEN_KEY] = ""
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATUS_KEY = intPreferencesKey("status")
        private val MESSAGE_KEY = stringPreferencesKey("message")
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val SCORE_KEY = intPreferencesKey("score")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}