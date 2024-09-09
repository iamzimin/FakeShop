package com.evg.product_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.usecase.ProductListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productListUseCases: ProductListUseCases,
): ViewModel() {
    private val filter = MutableStateFlow(ProductFilter())

    private val _products = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val products: StateFlow<PagingData<Product>> get() = _products

/*    private val _isProductsLoading = MutableStateFlow(true)
    val isProductsLoading: StateFlow<Boolean> = _isProductsLoading*/

    init {
        updateProducts()
    }

    fun updateProducts() {
        viewModelScope.launch {
            //_isProductsLoading.value = true
            productListUseCases.getAllProductsList.invoke(filter = filter.value)
                ?.cachedIn(viewModelScope)
                ?.collect { products ->
                    _products.value = products
                    //_isProductsLoading.value = false
                }
        }
    }

}