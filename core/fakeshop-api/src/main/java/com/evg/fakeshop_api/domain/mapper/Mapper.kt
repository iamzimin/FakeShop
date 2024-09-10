package com.evg.fakeshop_api.domain.mapper

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.SpecificationDBO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.SpecificationResponse

fun ProductResponse.toProductDBO(): ProductDBO {
    return ProductDBO(
        id = this.id,
        name = this.name,
        category = this.category,
        price = this.price,
        description = this.description,
        discountedPrice = this.discountedPrice,
        images = this.images,
        productRating = this.productRating,
        brand = this.brand,
        productSpecifications = this.productSpecifications.mapNotNull { it?.toSpecificationDBO() }
    )
}

fun SpecificationResponse.toSpecificationDBO(): SpecificationDBO {
    return SpecificationDBO(
        key = this.key,
        value = this.value
    )
}