package com.evg.registration.di

import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.registration.data.repository.RegistrationRepositoryImpl
import com.evg.registration.domain.repository.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegistrationModule {

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        fakeShopApiRepository: FakeShopApiRepository
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(
            fakeShopApiRepository = fakeShopApiRepository,
        )
    }
}