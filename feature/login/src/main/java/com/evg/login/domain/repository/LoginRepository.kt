package com.evg.login.domain.repository

import com.evg.fakeshop_api.domain.LoginError
import com.evg.fakeshop_api.domain.Result
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun loginUser(user: User): Flow<Result<LoginStatus, LoginError>>
    fun saveUserToken(token: String)
}