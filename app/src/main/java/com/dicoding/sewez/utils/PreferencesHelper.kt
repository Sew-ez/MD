package com.dicoding.sewez.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREFS_NAME = "dicoding_sewez_prefs"
    private const val KEY_TOKEN = "token"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginSession(context: Context, token: String) {
        val editor = getPrefs(context).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getLoginSession(context: Context): String? {
        return getPrefs(context).getString(KEY_TOKEN, null)
    }

    fun clearLoginSession(context: Context) {
        val editor = getPrefs(context).edit()
        editor.remove(KEY_TOKEN)
        editor.apply()
    }
}