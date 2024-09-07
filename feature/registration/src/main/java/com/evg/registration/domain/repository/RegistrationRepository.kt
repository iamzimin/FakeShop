package com.evg.registration.domain.repository

import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun registrationUser(user: User): Flow<RegistrationStatus>
}