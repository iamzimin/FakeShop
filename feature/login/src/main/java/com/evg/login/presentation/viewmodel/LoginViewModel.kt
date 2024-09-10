package com.evg.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User
import com.evg.login.domain.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
): ViewModel() {
    fun loginUser(user: User, loginCallback: (LoginStatus) -> Unit) {
        viewModelScope.launch {
            loginUseCases.loginUseCase.invoke(user = user)
                .collect { response ->
                    if (response.status == "success") {
                        response.token?.let { loginUseCases.saveUserTokenUseCase.invoke(token = it) }
                    }
                    loginCallback(response)
                }
        }
    }
}