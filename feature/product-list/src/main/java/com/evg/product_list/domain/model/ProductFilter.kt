package com.evg.product_list.domain.model

data class ProductFilter(
    var limit: Int = 10,
    var category: String? = null,
    var sort: SortType? = null,
)

enum class SortType {
    ASCENDING,
    DECENDING,
}