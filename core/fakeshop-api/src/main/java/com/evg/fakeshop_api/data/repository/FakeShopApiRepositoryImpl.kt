package com.evg.fakeshop_api.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.evg.database.domain.repository.DatabaseRepository
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
import retrofit2.HttpException
import retrofit2.Retrofit

class FakeShopApiRepositoryImpl(
    private val context: Context,
    fakeShopRetrofit: Retrofit,
    private val databaseRepository: DatabaseRepository,
): FakeShopApiRepository {
    private val fakeShopApi = fakeShopRetrofit.create(FakeShopApi::class.java)

    override suspend fun registrationUser(registrationBody: RegistrationBody): RegistrationResponse? {
        return try {
            val user = fakeShopApi.registrationUser(
                registrationBody = registrationBody
            )

            return user
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val registrationResponse: RegistrationResponse? = try {
                Gson().fromJson(errorBody, RegistrationResponse::class.java)
            } catch (e: Exception) {
                println(e)
                null
            }
            registrationResponse
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    override suspend fun loginUser(loginBody: LoginBody): LoginResponse? {
        return try {
            val user = fakeShopApi.loginUser(
                loginBody = loginBody
            )

            return user
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val loginResponse: LoginResponse? = try {
                Gson().fromJson(errorBody, LoginResponse::class.java)
            } catch (e: Exception) {
                println(e)
                null
            }
            loginResponse
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    override suspend fun getAllProductsListByPage(
        page: Int,
        filter: ProductFilterDTO
    ): ProductListPageResponse<ProductResponse>? {
        return try {
            val response = fakeShopApi.getProductsList(
                page = page,
                pageSize = filter.pageSize,
                category = filter.category,
                sort = filter.sort.value,
            )

            response?.productsList?.map { it.toProductDBO() }?.let {
                databaseRepository.insertProducts(
                    products = it
                )
            }

            return response
        } catch (e: Exception) {
            println(e.message) //TODO  retrofit2.HttpException: HTTP 521 Response{protocol=h2, code=521, message=, url=https://fakeshopapi-l2ng.onrender.com/app/v1/products?page=1&limit=10}
            null // java.net.SocketTimeoutException: timeout
        }
    }

    override suspend fun getProductById(id: String): ProductInfoResponse<ProductResponse>? {
        return try {
            val response = fakeShopApi.getProductById(
                id = id
            )

            response?.product?.toProductDBO()?.let {
                databaseRepository.insertProduct(
                    product = it
                )
            }

            return response
        } catch (e: Exception) {
            println(e.message) //TODO  retrofit2.HttpException: HTTP 521 Response{protocol=h2, code=521, message=, url=https://fakeshopapi-l2ng.onrender.com/app/v1/products?page=1&limit=10}
            null // java.net.SocketTimeoutException: timeout
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