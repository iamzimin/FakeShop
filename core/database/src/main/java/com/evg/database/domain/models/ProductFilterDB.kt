package com.evg.database.domain.models

data class ProductFilterDB(
    var pageSize: Int = 10, //TODO
    var category: String? = null,
    var sort: SortTypeDB? = null,
)

enum class SortTypeDB {
    ASCENDING,
    DECENDING,
}