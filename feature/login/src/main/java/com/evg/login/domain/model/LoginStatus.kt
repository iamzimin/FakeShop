package com.evg.login.domain.model

data class LoginStatus(
    val status: String,
    val message: String?,
    val token: String?,
)