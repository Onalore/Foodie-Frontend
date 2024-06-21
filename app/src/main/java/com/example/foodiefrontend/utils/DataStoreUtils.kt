package com.example.foodiefrontend.utils

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object DataStoreKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
}
