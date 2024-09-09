package com.evg.product_list.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.evg.database.data.ProductsPageSourceLocal
import com.evg.fakeshop_api.data.ProductsListPageSourceRemote
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.product_list.domain.mapper.toProduct
import com.evg.product_list.domain.mapper.toProductFilterDB
import com.evg.product_list.domain.mapper.toProductFilterDTO
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.repository.ProductListRepository
import com.evg.product_list.domain.model.ProductFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductListRepositoryImpl(
    private val fakeShopApi: FakeShopApiRepository,
    private val productsListPageSourceRemote: ProductsListPageSourceRemote,
    private val productsPageSourceLocal: ProductsPageSourceLocal,
): ProductListRepository {
    override suspend fun getAllProductsList(filter: ProductFilter): Flow<PagingData<Product>> {
        if (fakeShopApi.isInternetAvailable()) {
            return Pager(
                PagingConfig(
                pageSize = filter.pageSize,
            )) { productsListPageSourceRemote.apply { this.filter = filter.toProductFilterDTO() } }.flow.map { pagingData ->
                pagingData.map {
                    it.toProduct()
                }
            }
        } else {
            return Pager(
                PagingConfig(
                    pageSize = filter.pageSize,
                    initialLoadSize = filter.pageSize,
                )) { productsPageSourceLocal.apply { this.filter = filter.toProductFilterDB() } }.flow.map { pagingData ->
                pagingData.map {
                    it.toProduct()
                }
            }
        }
    }
}