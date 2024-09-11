package com.evg.fakeshop_api.domain.service

import com.evg.fakeshop_api.domain.models.AuthenticateResponse
import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.fakeshop_api.domain.models.ProductInfoResponse
import com.evg.fakeshop_api.domain.models.ProductListPageResponse
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeShopApi {
    @POST("app/v1/users")
    suspend fun registrationUser(
        @Body registrationBody: RegistrationBody
    ): RegistrationResponse

    @POST("app/v1/users/auth/login")
    suspend fun loginUser(
        @Body loginBody: LoginBody
    ): LoginResponse

    @GET("app/v1/users/auth/login")
    suspend fun authenticateUser(
        @Header("Authorization") token: String,
    ): AuthenticateResponse

    @GET("app/v1/products")
    suspend fun getProductsList(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int?, //TODO
        @Query("category") category: String?,
        @Query("sort") sort: String?,
    ): ProductListPageResponse<ProductResponse>

    @GET("app/v1/products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductInfoResponse<ProductResponse>
}