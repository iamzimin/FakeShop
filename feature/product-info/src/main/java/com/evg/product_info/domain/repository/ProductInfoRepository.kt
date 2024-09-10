package com.evg.product_info.domain.repository

import com.evg.product_info.domain.model.Product
import kotlinx.coroutines.flow.Flow


interface ProductInfoRepository {
    suspend fun getProductById(id: String): Flow<Product?>
}