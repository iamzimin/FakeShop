package com.evg.product_info.data.repository

import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.product_info.domain.mapper.toProduct
import com.evg.product_info.domain.model.Product
import com.evg.product_info.domain.repository.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductInfoRepositoryImpl(
    private val fakeShopApiRepository: FakeShopApiRepository,
    private val databaseRepository: DatabaseRepository,
): ProductInfoRepository {
    override suspend fun getProductById(id: String): Flow<Product?> {
        if (fakeShopApiRepository.isInternetAvailable()) {
            val response = fakeShopApiRepository.getProductById(id = id)
            return flow {
                emit(response?.product?.toProduct())
            }
        } else {
            val cashed = databaseRepository.getProductsById(id = id)
            return flow {
                emit(cashed?.toProduct())
            }
        }
    }
}