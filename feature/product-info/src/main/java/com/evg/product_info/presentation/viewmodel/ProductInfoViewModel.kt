package com.evg.product_info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
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

    private val _isProductLoading = MutableStateFlow(true)
    val isProductLoading: StateFlow<Boolean> = _isProductLoading

    fun getProductInfo(id: String, productCallback: (NetworkError) -> Unit) {
        viewModelScope.launch {
            _isProductLoading.value = true
            productInfoUseCases.getProductById.invoke(id = id)
                .collect { product ->
                    when(product) {
                        is Result.Error -> {
                            productCallback(product.error)
                        }
                        is Result.Success -> {
                            _productInfo.value = product.data
                        }
                    }
                    _isProductLoading.value = false
                }
        }
    }
}