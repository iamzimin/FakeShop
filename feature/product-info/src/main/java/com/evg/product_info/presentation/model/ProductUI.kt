package com.evg.product_info.presentation.model

data class ProductUI(
    val id: String,
    val imageURL: List<String>,
    val name: String,
    val description: String?,
    val price: String,
    val sale: String,
    val isHaveSale: Boolean,
    val productSpecifications: List<SpecificationUI>
)

data class SpecificationUI(
    val key: Spec,
    val value: String?,
)

enum class Spec {
    CATEGORY,
    CONDITION,
    SIZE,
    FABRIC,
    BRAND,
    COLOR,
}
