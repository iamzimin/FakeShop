package com.evg.database.domain.models

data class ProductFilterDB(
    var pageSize: Int = 10,
    var category: String? = null,
    var sort: SortTypeDB = SortTypeDB.DEFAULT,
)

enum class SortTypeDB {
    DEFAULT,
    ASCENDING,
    DESCENDING,
}