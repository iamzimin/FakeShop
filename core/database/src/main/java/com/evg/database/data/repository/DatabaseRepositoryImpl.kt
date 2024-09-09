package com.evg.database.data.repository

import com.evg.database.data.storage.ProductsDatabase
import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB
import com.evg.database.domain.repository.DatabaseRepository

class DatabaseRepositoryImpl(
    private val productsDatabase: ProductsDatabase,
): DatabaseRepository {
    override fun getAllProductsByPage(
        offset: Int,
        filter: ProductFilterDB
    ): List<ProductDBO> {
        return productsDatabase.productsDao.getAllProductsByPage(
            pageSize = filter.pageSize,
            offset = offset,
            category = filter.category,
            sort = filter.sort,
        )
    }

    override suspend fun insertProduct(product: ProductDBO) {
        productsDatabase.productsDao.insertProduct(product = product)
    }

    override suspend fun insertProducts(products: List<ProductDBO>) {
        productsDatabase.productsDao.insertProducts(products = products)
    }

    override suspend fun getProductsById(id: String): ProductDBO? {
        return productsDatabase.productsDao.getProductById(id = id)
    }
}