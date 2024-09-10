package com.evg.product_list.presentation.model

data class ProductUI(
    val id: String,
    val imageURL: String?,
    val name: String,
    val price: String,
    val sale: String,
    val isHaveSale: Boolean,
)
