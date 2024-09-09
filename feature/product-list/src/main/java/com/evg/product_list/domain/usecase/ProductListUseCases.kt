package com.evg.product_list.domain.usecase

import javax.inject.Inject

data class ProductListUseCases @Inject constructor(
    val getAllProductsByPage: GetAllProductsByPage,
)