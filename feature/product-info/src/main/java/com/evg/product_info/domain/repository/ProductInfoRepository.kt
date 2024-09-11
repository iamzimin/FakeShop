package com.evg.product_info.domain.repository

import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.product_info.domain.model.Product
import kotlinx.coroutines.flow.Flow


interface ProductInfoRepository {
    suspend fun getProductById(id: String): Flow<Result<Product?, NetworkError>>
}