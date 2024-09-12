package com.evg.registration.domain.model

import com.evg.fakeshop_api.domain.utils.RegistrationError

sealed class RegistrationState {
    data object Success : RegistrationState()
    data class Error(val error: RegistrationError) : RegistrationState()
}