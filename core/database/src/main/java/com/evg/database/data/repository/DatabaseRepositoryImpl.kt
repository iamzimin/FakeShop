package com.evg.database.data.repository

import com.evg.database.data.storage.ProductsDatabase
import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB
import com.evg.database.domain.repository.DatabaseRepository

class DatabaseRepositoryImpl(
    private val productsDatabase: ProductsDatabase,
): DatabaseRepository {

    /**
     * Возвращает список продуктов с учетом смещения [offset] и фильтра [filter].
     */
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

    /**
     * Вставляет продукт в базу данных.
     */
    override suspend fun insertProduct(product: ProductDBO) {
        productsDatabase.productsDao.insertProduct(product = product)
    }

    /**
     * Вставляет список продуктов в базу данных.
     */
    override suspend fun insertProducts(products: List<ProductDBO>) {
        productsDatabase.productsDao.insertProducts(products = products)
    }

    /**
     * Возвращает продукт по его идентификатору [id] или null, если продукт не найден.
     */
    override suspend fun getProductsById(id: String): ProductDBO? {
        return productsDatabase.productsDao.getProductById(id = id)
    }
}