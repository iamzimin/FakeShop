package com.evg.login.data.repository

import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.login.domain.mapper.toLoginBody
import com.evg.login.domain.mapper.toLoginStatus
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import com.evg.login.domain.repository.LoginRepository
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val fakeShopApiRepository: FakeShopApiRepository,
    private val sharedPrefsRepository: SharedPrefsRepository,
): LoginRepository {
    override suspend fun loginUser(user: User): Flow<LoginStatus> {
        return flow {
            emit(fakeShopApiRepository.loginUser(loginBody = user.toLoginBody()).toLoginStatus())
        }
    }

    override fun saveUserToken(token: String) {
        sharedPrefsRepository.saveUserToken(token = token)
    }
}