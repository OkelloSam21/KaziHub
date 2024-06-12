package com.samuelokello.kazihub.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
@OptIn(ExperimentalCoroutinesApi::class)
class LocalPreference
@Inject
constructor(val context: Context, val dataStore: DataStore<Preferences>) {

    enum class NameKeys {
        PROFILE,
        ACCOUNT_TYPE,
        ON_BOARDED
    }

    object Keys {
        val profile = stringPreferencesKey(name = NameKeys.PROFILE.name)
        val accountType = stringPreferencesKey(name = NameKeys.ACCOUNT_TYPE.name)
        val onBoarded = booleanPreferencesKey(name = NameKeys.ON_BOARDED.name)
    }


    val hasUserOnBoarded: Flow<Any> =
        dataStore.data.mapLatest { preferences -> preferences[Keys.onBoarded] ?: false }

    val accountType: Flow<String?> =
        dataStore.data.mapLatest { it[Keys.accountType] }

//    val profile: Flow<ProfileEntity?> = dataStore.data.map { preferences -> preferences[Keys.profile]?:""}

    suspend fun setOnBoarded(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.onBoarded] = value
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch {  exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                val onBoardingState = it[Keys.onBoarded] ?: false
                onBoardingState
            }
    }
}