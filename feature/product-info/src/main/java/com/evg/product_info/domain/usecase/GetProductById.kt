package com.evg.product_info.domain.usecase

import com.evg.product_info.domain.model.Product
import com.evg.product_info.domain.repository.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductById @Inject constructor(
    private val productInfoRepository: ProductInfoRepository
) {
    suspend fun invoke(id: String): Flow<Product?> {
        return productInfoRepository.getProductById(id = id)
    }
}