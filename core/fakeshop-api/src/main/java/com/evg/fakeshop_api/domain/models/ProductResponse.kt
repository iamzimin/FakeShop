package com.evg.fakeshop_api.domain.models

import com.evg.fakeshop_api.domain.mapper.ProductSpecificationsResponseDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: List<String>,
    @SerializedName("price") val price: Int,
    @SerializedName("discounted_price") val discountedPrice: Int,
    @SerializedName("images") val images: List<String>,
    @SerializedName("description") val description: String,
    @SerializedName("product_rating") val productRating: Double,
    @SerializedName("brand") val brand: String?,
    @SerializedName("product_specifications") val productSpecifications: List<SpecificationResponse?>
)

@JsonAdapter(ProductSpecificationsResponseDeserializer::class)
data class SpecificationResponse(
    @SerializedName("key") val key: String?,
    @SerializedName("value") val value: String
)
