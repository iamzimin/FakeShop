package com.evg.shared_prefs.data.repository

import android.content.Context
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class SharedPrefsRepositoryImpl(
    context: Context,
): SharedPrefsRepository {
    private val sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

    override fun saveUserToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("userToken", token)
            apply()
        }
    }

    override fun getUserToken(): String? {
        val userToken = sharedPreferences.getString("userToken", null)
        return userToken
    }

    override fun resetUserToken() {
        with(sharedPreferences.edit()) {
            putString("userToken", null)
            apply()
        }
    }

}