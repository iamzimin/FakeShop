package com.evg.fakeshop_api.domain.models

import com.google.gson.annotations.SerializedName

data class AuthenticateResponse (
    @SerializedName("status") val status: String,
    @SerializedName("token") val token: String?,
)