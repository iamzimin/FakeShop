package com.evg.database.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ProductDBO(
    @PrimaryKey val id: String,
    val name: String,
    val category: List<String>,
    val price: Int,
    val description: String,
    val discountedPrice: Int,
    val images: List<String>,
    val productRating: Double,
    val brand: String?,
    val productSpecifications: List<SpecificationDBO>
)

@Serializable
data class SpecificationDBO(
    val key: String?,
    val value: String
)
