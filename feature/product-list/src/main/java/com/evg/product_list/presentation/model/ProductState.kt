package com.evg.product_list.presentation.model

import com.evg.fakeshop_api.domain.NetworkError
import com.evg.product_list.domain.model.Product

sealed class ProductState {
    data class Success(val product: Product) : ProductState()
    data class Error(val error: NetworkError) : ProductState()
}