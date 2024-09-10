package com.evg.product_info.di

import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.product_info.data.repository.ProductInfoRepositoryImpl
import com.evg.product_info.domain.repository.ProductInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductInfoModule {

    @Provides
    @Singleton
    fun provideProductListModule(
        fakeShopApiRepository: FakeShopApiRepository,
        databaseRepository: DatabaseRepository,
    ): ProductInfoRepository {
        return ProductInfoRepositoryImpl(
            fakeShopApiRepository = fakeShopApiRepository,
            databaseRepository = databaseRepository,
        )
    }
}