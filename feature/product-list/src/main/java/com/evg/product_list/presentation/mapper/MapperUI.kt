package com.evg.product_list.presentation.mapper

import com.evg.product_list.domain.model.Product
import com.evg.product_list.presentation.model.ProductUI
import java.text.NumberFormat
import java.util.Locale

fun Product.toProductUI(): ProductUI {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))

    return ProductUI(
        id = this.id,
        imageURL = this.images.getOrNull(0),
        name = this.name,
        price = "${numberFormat.format(this.price)} ₽",
        sale = "${numberFormat.format(this.discountedPrice)} ₽",
        isHaveSale = this.price > this.discountedPrice
    )
}