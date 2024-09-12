package com.evg.shared_prefs.data.repository

import android.content.Context
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class SharedPrefsRepositoryImpl(
    context: Context,
): SharedPrefsRepository {
    private val sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

    /**
     * Сохраняет токен пользователя [token] в SharedPreferences.
     */
    override fun saveUserToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("userToken", token)
            apply()
        }
    }

    /**
     * Возвращает токен пользователя из SharedPreferences или null, если токен не найден.
     */
    override fun getUserToken(): String? {
        val userToken = sharedPreferences.getString("userToken", null)
        return userToken
    }

    /**
     * Сбрасывает токен пользователя в SharedPreferences.
     */
    override fun resetUserToken() {
        with(sharedPreferences.edit()) {
            putString("userToken", null)
            apply()
        }
    }
}