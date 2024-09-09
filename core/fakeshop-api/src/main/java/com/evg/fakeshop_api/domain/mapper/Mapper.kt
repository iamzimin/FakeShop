package com.evg.fakeshop_api.domain.mapper

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.SpecificationDBO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.SpecificationResponse

fun ProductResponse.toProduct(): ProductDBO {
    return ProductDBO(
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

fun SpecificationResponse.toSpecification(): SpecificationDBO {
    return SpecificationDBO(
        key = this.key,
        value = this.value
    )
}