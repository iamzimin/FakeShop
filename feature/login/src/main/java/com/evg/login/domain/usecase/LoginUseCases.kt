package com.evg.login.domain.usecase

import javax.inject.Inject

class LoginUseCases @Inject constructor(
    val loginUseCase: LoginUseCase,
    val saveUserTokenUseCase: SaveUserTokenUseCase,
)