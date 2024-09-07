package com.evg.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import com.evg.registration.domain.usecase.RegistrationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases
): ViewModel() {
    fun registrationUser(user: User, callback: (RegistrationStatus) -> Unit) {
        viewModelScope.launch {
            registrationUseCases.registrationUseCase.invoke(user = user)
                .collect { response ->
                    callback(response)
                }
        }
    }
}