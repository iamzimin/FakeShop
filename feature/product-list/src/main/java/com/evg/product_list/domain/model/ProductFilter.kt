package com.evg.product_list.domain.model

data class ProductFilter(
    var pageSize: Int = 10,
    var category: String? = null,
    var sort: SortType = SortType.DEFAULT,
)

enum class SortType {
    DEFAULT,
    ASCENDING,
    DESCENDING,
}