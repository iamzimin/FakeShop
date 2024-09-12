package com.evg.product_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.evg.fakeshop_api.domain.utils.NetworkError
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.usecase.ProductListUseCases
import com.evg.product_list.presentation.model.AuthenticateState
import com.evg.product_list.presentation.model.ProductState
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

    private val _products = MutableStateFlow<PagingData<ProductState>>(PagingData.empty())
    val products: StateFlow<PagingData<ProductState>> get() = _products

    private val _isAuthenticateLoading = MutableStateFlow(false)
    val isAuthenticateLoading: StateFlow<Boolean> = _isAuthenticateLoading

    init {
        updateProducts()
    }

    /**
     * Выполняет аутентификацию пользователя, используя сохраненный токен.
     * Обновляет состояние [_isAuthenticateLoading] и вызывает [authenticateCallback] с результатом.
     */
    fun authenticateUser(authenticateCallback: (AuthenticateState) -> Unit) {
        _isAuthenticateLoading.value = true
        val token = productListUseCases.getUserTokenUseCase.invoke()

        if (token != null) {
            viewModelScope.launch {
                productListUseCases.userAuthenticateUseCase.invoke(token = token)
                    .collect { result ->
                        when(result) {
                            is Result.Error -> {
                                productListUseCases.resetUserTokenUseCase.invoke()
                                authenticateCallback(AuthenticateState.Error(error = result.error))
                            }
                            is Result.Success -> authenticateCallback(AuthenticateState.Success)
                        }
                        _isAuthenticateLoading.value = false
                    }
            }
        } else {
            authenticateCallback(AuthenticateState.Error(error = NetworkError.UNKNOWN))
            _isAuthenticateLoading.value = false
        }
    }

    /**
     * Обновляет список продуктов, используя текущий фильтр.
     */
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

    /**
     * Устанавливает фильтр по категории [category] и обновляет список продуктов.
     */
    fun setCategoryFilter(category: String?) {
        filter.value = filter.value.copy(category = category)
        updateProducts()
    }

    /**
     * Устанавливает размер страницы [pageSize] и обновляет список продуктов.
     */
    fun setCategoryPageSize(pageSize: Int) {
        filter.value = filter.value.copy(pageSize = pageSize)
        updateProducts()
    }

    /**
     * Устанавливает тип сортировки [sortType] и обновляет список продуктов.
     */
    fun setSortType(sortType: SortType) {
        filter.value = filter.value.copy(sort = sortType)
        updateProducts()
    }
    /**
     * Возвращает текущий тип сортировки.
     */
    fun getSortType() = filter.value.sort

}