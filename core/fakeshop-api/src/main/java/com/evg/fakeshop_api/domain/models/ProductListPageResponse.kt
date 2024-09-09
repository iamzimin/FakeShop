package com.evg.fakeshop_api.domain.models

import com.google.gson.annotations.SerializedName

data class ProductListPageResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    @SerializedName("count") val count: Int?,
    @SerializedName("Data") val productsList: List<T>?,
)
