package com.evg.product_list.domain.usecase

import com.evg.fakeshop_api.domain.utils.NetworkError
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.product_list.domain.repository.ProductListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthenticateUseCase @Inject constructor(
    private val productListRepository: ProductListRepository
) {
    suspend fun invoke(token: String): Flow<Result<Any, NetworkError>> {
        return productListRepository.userAuthenticate(token = token)
    }
}