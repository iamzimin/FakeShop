package com.evg.product_list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.LocalNavHostController
import com.evg.product_list.presentation.viewmodel.ProductListViewModel

@Composable
fun ProductListRoot(
    viewModel: ProductListViewModel = hiltViewModel<ProductListViewModel>(),
) {
    val isAuthenticateLoading by viewModel.isAuthenticateLoading.collectAsState()
    val navController = LocalNavHostController.current

    ProductListScreen(
        products = viewModel.products.collectAsLazyPagingItems(),
        navController = navController,
        sortType = {
            viewModel.getSortType()
        },
        setSortType = { sortType ->
            viewModel.setSortType(
                sortType = sortType
            )
        },
        setCategoryFilter = { category ->
            viewModel.setCategoryFilter(
                category = category
            )
        },
        updateProducts = { viewModel.updateProducts() },
        setCategoryPageSize = { pageSize ->
            viewModel.setCategoryPageSize(
                pageSize = pageSize
            )
        },
        isAuthenticateLoading = isAuthenticateLoading,
        authenticateUser = { authenticateCallback ->
            viewModel.authenticateUser(
                authenticateCallback = authenticateCallback
            )
        }
    )
}