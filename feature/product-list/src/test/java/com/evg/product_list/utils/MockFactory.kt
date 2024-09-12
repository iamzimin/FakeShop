package com.evg.product_list.utils

import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.Specification

object MockFactory {

    fun createMockProduct(id: Int): Product {
        return Product(
            id = id.toString(),
            name = "Product $id",
            category = listOf("Category $id"),
            price = id * 1000,
            discountedPrice = id * 900,
            images = listOf("image_$id.jpg"),
            productRating = id.toDouble(),
            brand = "Brand $id",
            productSpecifications = listOf(Specification(key = "Key $id", value = "Value $id"))
        )
    }
}