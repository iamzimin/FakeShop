package com.evg.fakeshop_api.domain.models

data class ProductFilterDTO(
    var pageSize: Int = 10,
    var category: String? = null,
    var sort: SortTypeDTO = SortTypeDTO.DEFAULT,
)

enum class SortTypeDTO(val value: String?) {
    DEFAULT(null),
    ASCENDING("price"),
    DESCENDING("-price"),
}