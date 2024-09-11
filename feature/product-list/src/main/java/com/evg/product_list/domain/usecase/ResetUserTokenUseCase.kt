package com.evg.product_list.domain.usecase

import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import javax.inject.Inject

class ResetUserTokenUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    fun invoke() {
        sharedPrefsRepository.resetUserToken()
    }
}