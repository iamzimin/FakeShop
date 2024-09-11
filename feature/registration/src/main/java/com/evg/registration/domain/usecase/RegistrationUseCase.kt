package com.evg.registration.domain.usecase

import com.evg.fakeshop_api.domain.RegistrationError
import com.evg.fakeshop_api.domain.Result
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    suspend fun invoke(user: User): Flow<Result<RegistrationStatus, RegistrationError>> {
        return registrationRepository.registrationUser(user = user)
    }
}