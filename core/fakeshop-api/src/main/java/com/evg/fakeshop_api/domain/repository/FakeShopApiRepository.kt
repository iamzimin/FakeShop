package com.evg.fakeshop_api.domain.repository

import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse

interface FakeShopApiRepository {
    suspend fun registrationUser(registrationBody: RegistrationBody): RegistrationResponse?
    suspend fun loginUser(loginBody: LoginBody): LoginResponse?

    fun isInternetAvailable(): Boolean
}