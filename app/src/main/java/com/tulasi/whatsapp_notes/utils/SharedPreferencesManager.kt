package com.tulasi.whatsapp_notes.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val PREF_NAME = "WhatsAppNotePrefs"
    private val KEY_IS_FIRST_LAUNCH = "is_first_launch"
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        return sharedPrefs.getBoolean(KEY_IS_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(isFirst: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_IS_FIRST_LAUNCH, isFirst).apply()
    }
}