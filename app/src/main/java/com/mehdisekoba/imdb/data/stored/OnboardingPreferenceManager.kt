package com.mehdisekoba.imdb.data.stored

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mehdisekoba.imdb.utils.IS_ENABLED
import com.mehdisekoba.imdb.utils.ONBOARD
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingPreferenceManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val appContext = context.applicationContext

        companion object {
            private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(ONBOARD)
        }

        suspend fun saveOnboardingPreference(isEnabled: Boolean) {
            appContext.onboardingDataStore.edit {
                it[booleanPreferencesKey(IS_ENABLED)] = isEnabled
            }
        }

        val getOnboardingPreference: Flow<Boolean?> =
            appContext.onboardingDataStore.data.map {
                it[booleanPreferencesKey(IS_ENABLED)] ?: false
            }
    }
