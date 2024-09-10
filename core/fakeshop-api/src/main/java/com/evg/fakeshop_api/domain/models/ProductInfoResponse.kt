package com.evg.fakeshop_api.domain.models

import com.google.gson.annotations.SerializedName

data class ProductInfoResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val product: T?,
)
