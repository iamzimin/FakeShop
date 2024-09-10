package com.evg.fakeshop_api.di

import android.content.Context
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.fakeshop_api.data.repository.FakeShopApiRepositoryImpl
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeShopApiModule {

    @Provides
    @Singleton
    fun provideFakeShopApiRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply { //TODO
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://fakeshopapi-l2ng.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideFakeShopApiRepository(
        @ApplicationContext context: Context,
        fakeShopRetrofit: Retrofit,
        databaseRepository: DatabaseRepository,
    ): FakeShopApiRepository {
        return FakeShopApiRepositoryImpl(
            context = context,
            fakeShopRetrofit = fakeShopRetrofit,
            databaseRepository = databaseRepository,
        )
    }
}