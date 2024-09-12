package com.evg.product_list.presentation.mapper

import com.evg.product_list.domain.model.Product
import com.evg.product_list.presentation.model.ProductUI
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ProductToProductUITest {

    @Test
    fun `test Product to ProductUI conversion`() {
        // Given
        val product = Product(
            id = "1",
            name = "Product",
            category = listOf("Auto", "Table"),
            price = 3000,
            discountedPrice = 2500,
            images = listOf(
                "http://test.com/image1.jpg",
                "http://test.com/image2.jpg"
            ),
            productRating = 4.5,
            brand = "Brand1",
            productSpecifications = listOf()
        )

        val expected = ProductUI(
            id = "1",
            imageURL = "http://test.com/image1.jpg",
            name = "Product",
            price = "3 000 ₽",
            sale = "2 500 ₽",
            isHaveSale = true
        )

        // When
        val actual = product.toProductUI()

        // Then
        Assertions.assertEquals(expected, actual)
    }
}