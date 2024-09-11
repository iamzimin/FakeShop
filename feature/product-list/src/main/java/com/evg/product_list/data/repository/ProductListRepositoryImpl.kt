package com.evg.product_list.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.evg.database.data.ProductsPageSourceLocal
import com.evg.fakeshop_api.data.ProductsListPageSourceRemote
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.product_list.domain.mapper.toProduct
import com.evg.product_list.domain.mapper.toProductFilterDB
import com.evg.product_list.domain.mapper.toProductFilterDTO
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.repository.ProductListRepository
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.presentation.model.AuthenticateState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ProductListRepositoryImpl(
    private val fakeShopApi: FakeShopApiRepository,
    private val productsListPageSourceRemote: ProductsListPageSourceRemote,
    private val productsPageSourceLocal: ProductsPageSourceLocal,
): ProductListRepository {
    override suspend fun getAllProductsList(filter: ProductFilter): Flow<PagingData<Result<Product, NetworkError>>> {
        return if (fakeShopApi.isInternetAvailable()) {
            Pager(
                PagingConfig(
                    pageSize = filter.pageSize,
                )) { productsListPageSourceRemote.apply { this.filter = filter.toProductFilterDTO() } }
                .flow
                .map { pagingData ->
                    pagingData.map { productResponse ->
                        when (productResponse) {
                            is Result.Error -> {
                                Result.Error(productResponse.error)
                            }
                            is Result.Success -> {
                                Result.Success(productResponse.data.toProduct())
                            }
                        }
                    }
                }
        } else {
            Pager(
                PagingConfig(
                    pageSize = filter.pageSize,
                    initialLoadSize = filter.pageSize,
                )) { productsPageSourceLocal.apply { this.filter = filter.toProductFilterDB() } }
                .flow
                .map { pagingData ->
                    pagingData.map { productDB ->
                        try {
                            Result.Success(productDB.toProduct())
                        } catch (e: Exception) {
                            Result.Error(NetworkError.UNKNOWN)
                        } //TODO
                    }
                }
        }
    }

    override suspend fun userAuthenticate(token: String): Flow<Result<Unit, NetworkError>> {
        return flow {
            val value = when (val response = fakeShopApi.authenticateUser(token = token)) {
                is Result.Error -> Result.Error(response.error)
                is Result.Success -> Result.Success<Unit, NetworkError>(Unit)
            }
            emit(value)
        }
    }
}