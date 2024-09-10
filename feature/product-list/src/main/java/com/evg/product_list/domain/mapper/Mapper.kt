package com.evg.product_list.domain.mapper

import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB
import com.evg.database.domain.models.SortTypeDB
import com.evg.database.domain.models.SpecificationDBO
import com.evg.fakeshop_api.domain.mapper.toSpecificationDBO
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.SortTypeDTO
import com.evg.fakeshop_api.domain.models.SpecificationResponse
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.model.Specification
import com.evg.product_list.presentation.model.ProductUI
import java.text.NumberFormat
import java.util.Locale

fun ProductFilter.toProductFilterDTO(): ProductFilterDTO {
    return ProductFilterDTO(
        pageSize = this.pageSize,
        category = this.category,
        sort = this.sort.toSortType()
    )
}

fun SortType.toSortType(): SortTypeDTO {
    return when (this) {
        SortType.DEFAULT -> SortTypeDTO.DEFAULT
        SortType.ASCENDING -> SortTypeDTO.ASCENDING
        SortType.DESCENDING -> SortTypeDTO.DESCENDING
    }
}



fun ProductFilter.toProductFilterDB(): ProductFilterDB {
    return ProductFilterDB(
        pageSize = this.pageSize,
        category = this.category,
        sort = this.sort.toSortTypeDB()
    )
}

fun SortType.toSortTypeDB(): SortTypeDB {
    return when (this) {
        SortType.DEFAULT -> SortTypeDB.DEFAULT
        SortType.ASCENDING -> SortTypeDB.ASCENDING
        SortType.DESCENDING -> SortTypeDB.DESCENDING
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
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))

    return ProductUI(
        imageURL = images.getOrNull(0),
        name = this.name,
        price = "${numberFormat.format(this.price)} ₽",
        sale = "${numberFormat.format(this.discountedPrice)} ₽",
        isHaveSale = this.price > this.discountedPrice
    )
}