package com.evg.registration.data.repository

import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.registration.domain.mapper.toRegistrationBody
import com.evg.registration.domain.mapper.toRegistrationStatus
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationRepositoryImpl(
    private val fakeShopApiRepository: FakeShopApiRepository,
): RegistrationRepository {
    override suspend fun registrationUser(user: User): Flow<RegistrationStatus> {
        return flow {
            emit(fakeShopApiRepository.registrationUser(registrationBody = user.toRegistrationBody()).toRegistrationStatus())
        }
    }
}