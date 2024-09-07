package com.evg.login.domain.repository

import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun loginUser(user: User): Flow<LoginStatus>
    fun saveUserToken(token: String)
}