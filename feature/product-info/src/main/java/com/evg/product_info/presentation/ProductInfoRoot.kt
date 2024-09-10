package com.evg.product_info.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.product_info.presentation.viewmodel.ProductInfoViewModel

@Composable
fun ProductInfoRoot(
    productId: String,
    viewModel: ProductInfoViewModel = hiltViewModel<ProductInfoViewModel>(),
) {
    var isInitialized by rememberSaveable { mutableStateOf(false) }

    val productInfo by viewModel.productInfo.collectAsState()

    if (!isInitialized) {
        LaunchedEffect(productId) {
            viewModel.getProductInfo(productId)
            isInitialized = true
        }
    }

    ProductInfoScreen(
        productUI = productInfo?.toProductUI(),
    )
}