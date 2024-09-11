package com.evg.fakeshop_api.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.domain.LoginError
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.RegistrationError
import com.evg.fakeshop_api.domain.Result
import com.evg.fakeshop_api.domain.mapper.toProductDBO
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
import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
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
            } //TODO
        } catch (e: SocketTimeoutException) {
            Result.Error(LoginError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(LoginError.UNKNOWN)
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

            //throw HttpException(Response.error<Any>(408, "Conflict error".toResponseBody(null)))

            response.productsList?.map { it.toProductDBO() }?.let {
                databaseRepository.insertProducts(
                    products = it
                )
            }

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

            response.product?.toProductDBO()?.let {
                databaseRepository.insertProduct(
                    product = it
                )
            }

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