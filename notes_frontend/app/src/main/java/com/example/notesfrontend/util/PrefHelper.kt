package com.example.notesfrontend.util

import android.content.Context

object PrefHelper {
    private const val PREFS_NAME = "notes_prefs"
    private const val KEY_TOKEN = "token"

    // PUBLIC_INTERFACE
    fun saveToken(context: Context, token: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_TOKEN, token).apply()
    }

    // PUBLIC_INTERFACE
    fun getToken(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
    }

    // PUBLIC_INTERFACE
    fun clearToken(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().remove(KEY_TOKEN).apply()
    }
}
