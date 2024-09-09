package com.evg.product_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.evg.product_list.domain.usecase.ProductListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productListUseCases: ProductListUseCases,
): ViewModel() {

}