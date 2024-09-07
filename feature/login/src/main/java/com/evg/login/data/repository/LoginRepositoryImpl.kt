package com.evg.login.data.repository

import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.login.domain.mapper.toLoginBody
import com.evg.login.domain.mapper.toLoginStatus
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import com.evg.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val fakeShopApiRepository: FakeShopApiRepository,
): LoginRepository {
    override suspend fun loginUser(user: User): Flow<LoginStatus> {
        return flow {
            emit(fakeShopApiRepository.loginUser(loginBody = user.toLoginBody()).toLoginStatus())
        }
    }
}