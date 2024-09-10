package com.evg.product_info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.product_info.domain.model.Product
import com.evg.product_info.domain.usecase.ProductInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductInfoViewModel @Inject constructor(
    private val productInfoUseCases: ProductInfoUseCases,
): ViewModel() {
    private val _productInfo = MutableStateFlow<Product?>(null)
    val productInfo: StateFlow<Product?> get() = _productInfo

    private val _isInfoLoading = MutableStateFlow(true)
    val isInfoLoading: StateFlow<Boolean> = _isInfoLoading

    fun getProductInfo(id: String) {
        viewModelScope.launch {
            _isInfoLoading.value = true
            productInfoUseCases.getProductById.invoke(id = id)
                .collect { product ->
                    _productInfo.value = product
                    _isInfoLoading.value = false
                }
        }
    }
}