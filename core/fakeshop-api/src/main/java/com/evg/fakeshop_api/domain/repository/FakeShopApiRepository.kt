package com.evg.fakeshop_api.domain.repository

import com.evg.fakeshop_api.domain.LoginError
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.RegistrationError
import com.evg.fakeshop_api.domain.Result
import com.evg.fakeshop_api.domain.models.AuthenticateResponse
import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductInfoResponse
import com.evg.fakeshop_api.domain.models.ProductListPageResponse
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse

interface FakeShopApiRepository {
    suspend fun registrationUser(registrationBody: RegistrationBody): Result<RegistrationResponse, RegistrationError>
    suspend fun loginUser(loginBody: LoginBody): Result<LoginResponse, LoginError>
    suspend fun authenticateUser(token: String): Result<AuthenticateResponse, NetworkError>

    suspend fun getAllProductsListByPage(page: Int, filter: ProductFilterDTO): Result<ProductListPageResponse<ProductResponse>, NetworkError>
    suspend fun getProductById(id: String): Result<ProductInfoResponse<ProductResponse>, NetworkError>

    fun isInternetAvailable(): Boolean
}