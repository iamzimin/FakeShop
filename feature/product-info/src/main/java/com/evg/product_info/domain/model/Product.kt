package com.evg.product_info.domain.model

data class Product(
    val id: String,
    val name: String,
    val category: List<String>,
    val price: Int,
    val description: String?,
    val discountedPrice: Int,
    val images: List<String>,
    val productRating: Double,
    val brand: String?,
    val productSpecifications: List<Specification>
)

data class Specification(
    val key: String?,
    val value: String
)
