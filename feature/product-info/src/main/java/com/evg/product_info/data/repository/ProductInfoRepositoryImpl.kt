package com.evg.product_info.data.repository

import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.domain.utils.NetworkError
import com.evg.fakeshop_api.domain.utils.Result
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
    override suspend fun getProductById(id: String): Flow<Result<Product?, NetworkError>> {
        if (fakeShopApiRepository.isInternetAvailable()) {
            val result = when(
                val response = fakeShopApiRepository.getProductById(id = id)
            ) {
                is Result.Error -> Result.Error<Product, NetworkError>(response.error)
                is Result.Success -> Result.Success(response.data.product?.toProduct())
            }
            return flow {
                emit(result)
            }
        } else {
            val cashed = databaseRepository.getProductsById(id = id)
            return flow {
                val product = try {
                    Result.Success<Product?, NetworkError>(cashed?.toProduct())
                } catch (e: Exception) {
                    Result.Error(NetworkError.UNKNOWN)
                }
                emit(product)
            }
        }
    }
}