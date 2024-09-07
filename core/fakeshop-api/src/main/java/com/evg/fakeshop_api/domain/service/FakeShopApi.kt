package com.evg.fakeshop_api.domain.service

import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FakeShopApi {
    @POST("app/v1/users")
    suspend fun registrationUser(
        @Body registrationBody: RegistrationBody
    ): RegistrationResponse?
}