package com.evg.login.domain.usecase

import com.evg.fakeshop_api.domain.LoginError
import com.evg.fakeshop_api.domain.Result
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import com.evg.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend fun invoke(user: User): Flow<Result<LoginStatus, LoginError>> {
        return loginRepository.loginUser(user = user)
    }
}