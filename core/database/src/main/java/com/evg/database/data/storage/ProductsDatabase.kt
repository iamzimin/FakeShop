package com.evg.database.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.evg.database.domain.conventers.ProductsConverter
import com.evg.database.domain.models.ProductDBO

@Database(
    entities = [ProductDBO::class],
    version = 1
)
@TypeConverters(ProductsConverter::class)
abstract class ProductsDatabase: RoomDatabase() {
    abstract val productsDao: ProductsDao

    companion object {
        const val DATABASE_NAME = "products"
    }
}