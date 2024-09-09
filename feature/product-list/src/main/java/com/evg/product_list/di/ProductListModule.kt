package com.evg.product_list.di

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
    ): ProductListRepository {
        return ProductListRepositoryImpl(

        )
    }
}