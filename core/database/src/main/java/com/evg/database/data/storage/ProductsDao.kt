package com.evg.database.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.SortTypeDB

@Dao
interface ProductsDao {
    @Query(
        """SELECT * FROM productdbo
            WHERE (:category IS NULL OR category LIKE '%' || :category || '%') 
            ORDER BY 
                CASE WHEN :sort = 'ASCENDING' THEN price END ASC,
                CASE WHEN :sort = 'DESCENDING' THEN price END DESC
            LIMIT :pageSize OFFSET :offset;"""
    )
    fun getAllProductsByPage(
        pageSize: Int,
        offset: Int,
        category: String?,
        sort: SortTypeDB?,
    ): List<ProductDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductDBO>)

    @Query("SELECT * FROM productdbo WHERE id = :id")
    suspend fun getProductById(id: String): ProductDBO?
}