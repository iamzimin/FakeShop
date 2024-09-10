package com.evg.product_info.presentation

import com.evg.product_info.domain.model.Product
import com.evg.product_info.presentation.model.ProductUI
import com.evg.product_info.presentation.model.Spec
import com.evg.product_info.presentation.model.SpecificationUI
import java.util.Locale

fun Product.toProductUI(): ProductUI {
    val numberFormat = java.text.NumberFormat.getNumberInstance(Locale("ru", "RU"))

    return ProductUI(
        id = this.id,
        imageURL = this.images,
        name = this.name,
        description = this.description,
        price = "${numberFormat.format(this.price)} ₽",
        sale = "${numberFormat.format(this.discountedPrice)} ₽",
        isHaveSale = this.price > this.discountedPrice,
        productSpecifications = Spec.entries.map { spec ->
            SpecificationUI(
                key = spec,
                value = when (spec) {
                    Spec.BRAND -> this.brand
                    Spec.CATEGORY -> this.category.getOrNull(0)
                    else -> this.productSpecifications.find { specification ->
                        specification.key.equals(spec.toKey(), ignoreCase = true)
                    }?.value
                }
            )
        }
    )
}

fun Spec.toKey(): String {
    return when (this) {
        Spec.CATEGORY -> "Category"
        Spec.CONDITION -> "Condition"
        Spec.SIZE -> "Size"
        Spec.FABRIC -> "Fabric"
        Spec.BRAND -> "Brand"
        Spec.COLOR -> "Color"
    }
}