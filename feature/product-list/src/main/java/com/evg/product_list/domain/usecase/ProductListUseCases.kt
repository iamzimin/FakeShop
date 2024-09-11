package com.evg.product_list.domain.usecase

import javax.inject.Inject

data class ProductListUseCases @Inject constructor(
    val getAllProductsList: GetAllProductsList,
    val userAuthenticateUseCase: UserAuthenticateUseCase,
    val getUserTokenUseCase: GetUserTokenUseCase,
    val resetUserTokenUseCase: ResetUserTokenUseCase,
)