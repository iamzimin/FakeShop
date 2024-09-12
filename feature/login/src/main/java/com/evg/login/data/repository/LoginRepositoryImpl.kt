package com.evg.login.data.repository

import com.evg.fakeshop_api.domain.utils.LoginError
import com.evg.fakeshop_api.domain.utils.Result
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
    override suspend fun loginUser(user: User): Flow<Result<LoginStatus, LoginError>> {
        return flow {
            val logUser = fakeShopApiRepository.loginUser(loginBody = user.toLoginBody())
            val result = when (logUser) {
                is Result.Error -> Result.Error<LoginStatus, LoginError>(logUser.error)
                is Result.Success -> Result.Success(logUser.data.toLoginStatus())
            }
            emit(result)
        }
    }

    override fun saveUserToken(token: String) {
        sharedPrefsRepository.saveUserToken(token = token)
    }
}