package com.evg.shared_prefs.domain.repository

interface SharedPrefsRepository {
    fun saveUserToken(token: String)
    fun getUserToken(): String?
}