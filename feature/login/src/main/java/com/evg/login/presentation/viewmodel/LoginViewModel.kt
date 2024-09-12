package com.evg.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.login.domain.model.LoginState
import com.evg.login.domain.model.User
import com.evg.login.domain.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
): ViewModel() {
    fun loginUser(user: User, loginCallback: (LoginState) -> Unit) {
        viewModelScope.launch {
            loginUseCases.loginUseCase.invoke(user = user)
                .collect { response ->
                    when(response) {
                        is Result.Error -> {
                            loginCallback(LoginState.Error(error = response.error))
                        }
                        is Result.Success -> {
                            response.data.token?.let { loginUseCases.saveUserTokenUseCase.invoke(token = it) }
                            loginCallback(LoginState.Success)
                        }
                    }
                }
        }
    }
}