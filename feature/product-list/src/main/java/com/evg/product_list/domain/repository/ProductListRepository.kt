package com.evg.product_list.domain.repository

import androidx.paging.PagingData
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import kotlinx.coroutines.flow.Flow

interface ProductListRepository {
    suspend fun getAllProductsList(filter: ProductFilter): Flow<PagingData<Result<Product, NetworkError>>>
}