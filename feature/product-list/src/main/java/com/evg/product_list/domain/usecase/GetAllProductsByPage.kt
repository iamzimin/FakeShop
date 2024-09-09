package com.evg.product_list.domain.usecase

import com.evg.product_list.domain.repository.ProductListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsByPage @Inject constructor(
    private val productListRepository: ProductListRepository
) {
    suspend fun invoke() {
        //return productListRepository
    }
}