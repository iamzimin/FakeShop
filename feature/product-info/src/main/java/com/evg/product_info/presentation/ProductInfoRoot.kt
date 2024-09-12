package com.evg.product_info.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.product_info.presentation.mapper.toProductUI
import com.evg.product_info.presentation.viewmodel.ProductInfoViewModel

@Composable
fun ProductInfoRoot(
    productId: String,
    viewModel: ProductInfoViewModel = hiltViewModel<ProductInfoViewModel>(),
) {
    val productInfo by viewModel.productInfo.collectAsState()
    val isProductLoading by viewModel.isProductLoading.collectAsState()

    ProductInfoScreen(
        productId = productId,
        productUI = productInfo?.toProductUI(),
        isProductLoading = isProductLoading,
        getProductInfo = { id, productCallback ->
            viewModel.getProductInfo(
                id = id,
                productCallback = productCallback,
            )
        }
    )
}