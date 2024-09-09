package com.evg.product_list.domain.mapper

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB
import com.evg.database.domain.models.SortTypeDB
import com.evg.database.domain.models.SpecificationDBO
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.SortTypeDTO
import com.evg.fakeshop_api.domain.models.SpecificationResponse
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.model.Specification
import com.evg.product_list.presentation.model.ProductUI

fun ProductFilter.toProductFilterDTO(): ProductFilterDTO {
    return ProductFilterDTO(
        pageSize = this.pageSize,
        category = this.category,
        sort = this.sort?.toSortType()
    )
}

fun SortType.toSortType(): SortTypeDTO {
    return when (this) {
        SortType.ASCENDING -> SortTypeDTO.ASCENDING
        SortType.DECENDING -> SortTypeDTO.DECENDING
    }
}



fun ProductFilter.toProductFilterDB(): ProductFilterDB {
    return ProductFilterDB(
        pageSize = this.pageSize,
        category = this.category,
        sort = this.sort?.toSortTypeDB()
    )
}

fun SortType.toSortTypeDB(): SortTypeDB {
    return when (this) {
        SortType.ASCENDING -> SortTypeDB.ASCENDING
        SortType.DECENDING -> SortTypeDB.DECENDING
    }
}



fun ProductResponse.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = this.category,
        price = this.price,
        discountedPrice = this.discountedPrice,
        images = this.images,
        productRating = this.productRating,
        brand = this.brand,
        productSpecifications = this.productSpecifications.map { it.toSpecification() }
    )
}

fun SpecificationResponse.toSpecification(): Specification {
    return Specification(
        key = this.key,
        value = this.value
    )
}



fun ProductDBO.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = this.category,
        price = this.price,
        discountedPrice = this.discountedPrice,
        images = this.images,
        productRating = this.productRating,
        brand = this.brand,
        productSpecifications = this.productSpecifications.map { it.toSpecification() }
    )
}

fun SpecificationDBO.toSpecification(): Specification {
    return Specification(
        key = this.key,
        value = this.value
    )
}



fun Product.toProductUI(): ProductUI {
    return ProductUI(
        imageURL = images.getOrNull(0),
        name = this.name,
        price = this.price,
        sale = this.discountedPrice,
    )
}