package com.example.firstproject.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager (private val appCtx: Context) {

    companion object {
        private const val PREF_NAME = "session_pref"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val DEFAULT_USER_ID = -1
    }

    private val sharedPref: SharedPreferences = appCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(userId: Int, email: String) {
        sharedPref.edit().apply() {
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun isLoggedIn() : Boolean {
        return sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId() : Int {
        return sharedPref.getInt(KEY_USER_ID, DEFAULT_USER_ID)
    }

    fun getUserEmail() : String? {
        return sharedPref.getString(KEY_USER_EMAIL, null)
    }

    fun logout(){
        sharedPref.edit().clear().apply()
    }
}