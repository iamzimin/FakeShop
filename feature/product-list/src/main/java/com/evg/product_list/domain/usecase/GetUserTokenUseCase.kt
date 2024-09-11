package com.evg.product_list.domain.usecase

import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    fun invoke(): String? {
        return sharedPrefsRepository.getUserToken()
    }
}