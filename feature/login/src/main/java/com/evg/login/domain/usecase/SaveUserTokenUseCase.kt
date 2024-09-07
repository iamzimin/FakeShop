package com.evg.login.domain.usecase

import com.evg.login.domain.repository.LoginRepository
import javax.inject.Inject

class SaveUserTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    fun invoke(token: String) {
        return loginRepository.saveUserToken(token = token)
    }
}