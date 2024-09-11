package com.evg.product_list.domain.usecase

import androidx.paging.PagingData
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.repository.ProductListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsList @Inject constructor(
    private val productListRepository: ProductListRepository
) {
    suspend fun invoke(filter: ProductFilter): Flow<PagingData<Result<Product, NetworkError>>> {
        return productListRepository.getAllProductsList(filter = filter)
    }
}