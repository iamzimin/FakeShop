package com.evg.fakeshop_api.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.domain.utils.LoginError
import com.evg.fakeshop_api.domain.utils.NetworkError
import com.evg.fakeshop_api.domain.utils.RegistrationError
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.fakeshop_api.domain.mapper.toProductDBO
import com.evg.fakeshop_api.domain.models.AuthenticateResponse
import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductInfoResponse
import com.evg.fakeshop_api.domain.models.ProductListPageResponse
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.fakeshop_api.domain.service.FakeShopApi
import com.google.gson.JsonParseException
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.SocketTimeoutException

class FakeShopApiRepositoryImpl(
    private val context: Context,
    fakeShopRetrofit: Retrofit,
    private val databaseRepository: DatabaseRepository,
): FakeShopApiRepository {
    private val fakeShopApi = fakeShopRetrofit.create(FakeShopApi::class.java)

    override suspend fun registrationUser(
        registrationBody: RegistrationBody
    ): Result<RegistrationResponse, RegistrationError> {
        return try {
            val user = fakeShopApi.registrationUser(
                registrationBody = registrationBody
            )

            return Result.Success(user)
        } catch (e: JsonParseException) {
            Result.Error(RegistrationError.SERIALIZATION)
        } catch (e: HttpException) {
            when(e.code()) {
                408 -> Result.Error(RegistrationError.REQUEST_TIMEOUT)
                409 -> Result.Error(RegistrationError.EMAIL_EXIST)
                429 -> Result.Error(RegistrationError.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(RegistrationError.SERVER_ERROR)
                else -> Result.Error(RegistrationError.UNKNOWN)
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(RegistrationError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(RegistrationError.UNKNOWN)
        }
    }

    override suspend fun loginUser(
        loginBody: LoginBody
    ): Result<LoginResponse, LoginError> {
        return try {
            val user = fakeShopApi.loginUser(
                loginBody = loginBody
            )

            return Result.Success(user)
        } catch (e: JsonParseException) {
            Result.Error(LoginError.SERIALIZATION)
        } catch (e: HttpException) {
            when(e.code()) {
                401 -> Result.Error(LoginError.USER_NOT_FOUND)
                408 -> Result.Error(LoginError.REQUEST_TIMEOUT)
                429 -> Result.Error(LoginError.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(LoginError.SERVER_ERROR)
                else -> Result.Error(LoginError.UNKNOWN)
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(LoginError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(LoginError.UNKNOWN)
        }
    }

    override suspend fun authenticateUser(
        token: String
    ): Result<AuthenticateResponse, NetworkError> {
        return try {
            val response = fakeShopApi.authenticateUser(
                token = "Bearer $token"
            )

            return Result.Success(response)
        } catch (e: JsonParseException) {
            Result.Error(NetworkError.SERIALIZATION)
        } catch (e: HttpException) {
            when(e.code()) {
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }


    override suspend fun getAllProductsListByPage(
        page: Int,
        filter: ProductFilterDTO
    ): Result<ProductListPageResponse<ProductResponse>, NetworkError> {
        return try {
            val response = fakeShopApi.getProductsList(
                page = page,
                pageSize = filter.pageSize,
                category = filter.category,
                sort = filter.sort.value,
            )

            try {
                response.productsList?.map { it.toProductDBO() }?.let {
                    databaseRepository.insertProducts(
                        products = it
                    )
                }
            } catch (_: Exception) { }

            return Result.Success(response)
        } catch (e: JsonParseException) {
            Result.Error(NetworkError.SERIALIZATION)
        } catch (e: HttpException) {
            when(e.code()) {
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun getProductById(
        id: String
    ): Result<ProductInfoResponse<ProductResponse>, NetworkError> {
        return try {
            val response = fakeShopApi.getProductById(
                id = id
            )

            try {
                response.product?.toProductDBO()?.let {
                    databaseRepository.insertProduct(
                        product = it
                    )
                }
            } catch (_: Exception) { }

            return Result.Success(response)
        } catch (e: JsonParseException) {
            Result.Error(NetworkError.SERIALIZATION)
        } catch (e: HttpException) {
            when(e.code()) {
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }



    override fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}