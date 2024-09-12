package com.evg.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.registration.domain.model.RegistrationState
import com.evg.registration.domain.model.User
import com.evg.registration.domain.usecase.RegistrationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases
): ViewModel() {
    fun registrationUser(user: User, registrationCallback: (RegistrationState) -> Unit) {
        viewModelScope.launch {
            registrationUseCases.registrationUseCase.invoke(user = user)
                .collect { response ->
                    when(response) {
                        is Result.Error -> {
                            registrationCallback(RegistrationState.Error(error = response.error))
                        }
                        is Result.Success -> {
                            registrationCallback(RegistrationState.Success)
                        }
                    }
                }
        }
    }
}