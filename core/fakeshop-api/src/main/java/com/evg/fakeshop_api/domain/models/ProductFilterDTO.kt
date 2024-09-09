package com.evg.fakeshop_api.domain.models

data class ProductFilterDTO(
    var pageSize: Int = 10, //TODO
    var category: String? = null,
    var sort: SortTypeDTO? = null,
)

enum class SortTypeDTO(val value: String) {
    ASCENDING("+price"),
    DECENDING("-price"),
}