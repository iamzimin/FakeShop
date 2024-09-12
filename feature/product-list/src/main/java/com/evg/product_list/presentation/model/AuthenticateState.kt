package com.evg.product_list.presentation.model

import com.evg.fakeshop_api.domain.utils.NetworkError

sealed class AuthenticateState {
    data object Success : AuthenticateState()
    data class Error(val error: NetworkError) : AuthenticateState()
}
