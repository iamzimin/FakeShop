package com.evg.product_info.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.fakeshop_api.domain.NetworkError
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