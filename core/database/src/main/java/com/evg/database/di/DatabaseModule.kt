package com.evg.database.di

import android.content.Context
import androidx.room.Room
import com.evg.database.data.ProductsPageSourceLocal
import com.evg.database.data.repository.DatabaseRepositoryImpl
import com.evg.database.data.storage.ProductsDatabase
import com.evg.database.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductsDatabase(
        @ApplicationContext context: Context
    ) : ProductsDatabase {
        return Room.databaseBuilder(
            context,
            ProductsDatabase::class.java,
            ProductsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(
        productsDatabase: ProductsDatabase,
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(
            productsDatabase = productsDatabase,
        )
    }

    @Provides
    @Singleton
    fun provideProductsPageSourceLocal(
        databaseRepository: DatabaseRepository
    ): ProductsPageSourceLocal {
        return ProductsPageSourceLocal(
            databaseRepository = databaseRepository
        )
    }
}