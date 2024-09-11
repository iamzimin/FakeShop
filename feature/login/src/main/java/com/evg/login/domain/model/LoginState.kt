package com.evg.login.domain.model

import com.evg.fakeshop_api.domain.LoginError

sealed class LoginState {
    data object Success : LoginState()
    data class Error(val error: LoginError) : LoginState()
}