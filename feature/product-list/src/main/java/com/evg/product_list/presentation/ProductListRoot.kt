package com.evg.product_list.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.product_list.presentation.viewmodel.ProductListViewModel

@Composable
fun ProductListRoot(
    viewModel: ProductListViewModel = hiltViewModel<ProductListViewModel>(),
) {
    ProductListScreen(
        products = viewModel.products.collectAsLazyPagingItems(),
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
        }
    )
}