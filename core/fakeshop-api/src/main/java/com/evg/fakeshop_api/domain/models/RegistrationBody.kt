package com.evg.fakeshop_api.domain.models

import com.google.gson.annotations.SerializedName

data class RegistrationBody(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("cpassword") val cpassword: String,
)