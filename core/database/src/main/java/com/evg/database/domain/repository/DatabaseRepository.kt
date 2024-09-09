package com.evg.database.domain.repository

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB

interface DatabaseRepository {
    fun getAllProductsByPage(
        offset: Int,
        filter: ProductFilterDB,
    ): List<ProductDBO>
    suspend fun insertProduct(product: ProductDBO)
    suspend fun insertProducts(products: List<ProductDBO>)
    suspend fun getProductsById(id: String): ProductDBO?
}
