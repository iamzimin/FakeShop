package com.evg.registration.domain.repository

import com.evg.fakeshop_api.domain.RegistrationError
import com.evg.fakeshop_api.domain.Result
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun registrationUser(user: User): Flow<Result<RegistrationStatus, RegistrationError>>
}