package com.evg.product_info.domain.usecase

import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.product_info.domain.model.Product
import com.evg.product_info.domain.repository.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductById @Inject constructor(
    private val productInfoRepository: ProductInfoRepository
) {
    suspend fun invoke(id: String): Flow<Result<Product?, NetworkError>> {
        return productInfoRepository.getProductById(id = id)
    }
}