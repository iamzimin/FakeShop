package com.evg.product_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.usecase.ProductListUseCases
import com.evg.product_list.presentation.model.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productListUseCases: ProductListUseCases,
): ViewModel() {
    private val filter = MutableStateFlow(ProductFilter())

    private val _products = MutableStateFlow<PagingData<ProductState>>(PagingData.empty())
    val products: StateFlow<PagingData<ProductState>> get() = _products

    init {
        updateProducts()
    }

    fun updateProducts() {
        viewModelScope.launch {
            productListUseCases.getAllProductsList.invoke(filter = filter.value)
                .cachedIn(viewModelScope)
                .collect { products ->
                    val productStates = products.map { result ->
                        when (result) {
                            is Result.Error -> ProductState.Error(result.error)
                            is Result.Success -> ProductState.Success(result.data)
                        }
                    }
                    _products.value = productStates
                }
        }
    }

    fun setCategoryFilter(category: String?) {
        filter.value = filter.value.copy(category = category)
        updateProducts()
    }

    fun setCategoryPageSize(pageSize: Int) {
        filter.value = filter.value.copy(pageSize = pageSize)
        updateProducts()
    }

    fun setSortType(sortType: SortType) {
        filter.value = filter.value.copy(sort = sortType)
        updateProducts()
    }
    fun getSortType() = filter.value.sort

}