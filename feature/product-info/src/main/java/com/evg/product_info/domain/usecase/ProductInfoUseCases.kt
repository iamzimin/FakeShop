package com.evg.product_info.domain.usecase

import javax.inject.Inject

class ProductInfoUseCases @Inject constructor(
    val getProductById: GetProductById,
)