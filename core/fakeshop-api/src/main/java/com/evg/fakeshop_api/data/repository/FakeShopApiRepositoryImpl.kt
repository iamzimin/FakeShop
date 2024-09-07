package com.evg.fakeshop_api.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.fakeshop_api.domain.service.FakeShopApi
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit

class FakeShopApiRepositoryImpl(
    private val context: Context,
    fakeShopRetrofit: Retrofit,
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
                null
            }
            registrationResponse
        } catch (e: Exception) {
            //println(e)
            null
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