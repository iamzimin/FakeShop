package com.evg.product_info.domain.mapper

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.SpecificationDBO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.SpecificationResponse
import com.evg.product_info.domain.model.Product
import com.evg.product_info.domain.model.Specification

fun ProductResponse.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = this.category,
        price = this.price,
        description = this.description,
        discountedPrice = this.discountedPrice,
        images = this.images,
        productRating = this.productRating,
        brand = this.brand,
        productSpecifications = this.productSpecifications.mapNotNull { it?.toSpecification() }
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
        description = this.description,
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