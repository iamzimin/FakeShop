package com.evg.product_list.di

import com.evg.database.data.ProductsPageSourceLocal
import com.evg.fakeshop_api.data.ProductsListPageSourceRemote
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.product_list.data.repository.ProductListRepositoryImpl
import com.evg.product_list.domain.repository.ProductListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductListModule {

    @Provides
    @Singleton
    fun provideProductListModule(
        fakeShopApiRepository: FakeShopApiRepository,
        productsListPageSourceRemote: ProductsListPageSourceRemote,
        productsPageSourceLocal: ProductsPageSourceLocal,
    ): ProductListRepository {
        return ProductListRepositoryImpl(
            fakeShopApi = fakeShopApiRepository,
            productsListPageSourceRemote = productsListPageSourceRemote,
            productsPageSourceLocal = productsPageSourceLocal,
        )
    }
}