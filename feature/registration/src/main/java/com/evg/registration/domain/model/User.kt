package com.evg.registration.domain.model

data class User(
    val name: String,
    val email: String,
    val password: String,
    val cpassword: String,
)