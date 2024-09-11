package com.evg.fakeshop_api.domain.models

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    //@SerializedName("data") val data: RegistrationData?
)

/*
data class RegistrationData(
    @SerializedName("id") val id: String?,
)*/
