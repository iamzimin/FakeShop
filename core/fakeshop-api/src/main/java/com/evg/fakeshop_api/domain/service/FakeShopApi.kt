package com.evg.fakeshop_api.domain.service

import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductListPageResponse
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import com.evg.fakeshop_api.domain.models.SortTypeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FakeShopApi {
    @POST("app/v1/users")
    suspend fun registrationUser(
        @Body registrationBody: RegistrationBody
    ): RegistrationResponse?

    @POST("app/v1/users/auth/login")
    suspend fun loginUser(
        @Body loginBody: LoginBody
    ): LoginResponse?

    @GET("app/v1/products")
    suspend fun getProductsList(
        @Query("page") page: Int,
        @Query("limit") limit: Int?, //TODO
        @Query("category") category: String?,
        @Query("sort") sort: String?,
    ): ProductListPageResponse<ProductResponse>?
}