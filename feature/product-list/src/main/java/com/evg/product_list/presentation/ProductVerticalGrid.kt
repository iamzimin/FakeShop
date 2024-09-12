package com.evg.product_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.product_list.presentation.mapper.toProductUI
import com.evg.product_list.presentation.model.ProductState
import com.evg.resource.R
import com.evg.ui.theme.FakeShopTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProductVerticalGrid(
    updateProducts: () -> Unit,
    products: LazyPagingItems<ProductState>,
) {
    val context = LocalContext.current
    var isProductsLoadingError: Boolean by rememberSaveable { mutableStateOf(false) }
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)

    if (isProductsLoadingError) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = refreshingState,
            onRefresh = { updateProducts() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.products_loading_error),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    when (products.loadState.refresh) {
        is LoadState.Loading -> {
            isProductsLoadingError = false

            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                items(10) {
                    ProductTileShimmer()
                }
            }
        }

        is LoadState.Error -> {
            isProductsLoadingError = true
        }

        is LoadState.NotLoading -> {
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize(),
                state = refreshingState,
                onRefresh = { updateProducts() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                },
            ) {
                if (products.itemCount == 0) {
                    isProductsLoadingError = true
                }

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                ) {
                    items(
                        count = products.itemCount,
                    ) { index ->
                        val item = products[index]
                        if (item != null) {
                            when (item) {
                                is ProductState.Success -> {
                                    ProductTile(
                                        productUI = item.product.toProductUI()
                                    )
                                }

                                is ProductState.Error -> {
                                    isProductsLoadingError = true

                                    val errorMessage = when (item.error) {
                                        NetworkError.REQUEST_TIMEOUT -> stringResource(R.string.request_timeout)
                                        NetworkError.TOO_MANY_REQUESTS -> stringResource(R.string.too_many_requests)
                                        NetworkError.SERVER_ERROR -> stringResource(R.string.server_error)
                                        NetworkError.SERIALIZATION -> stringResource(R.string.serialization_error)
                                        NetworkError.UNKNOWN -> stringResource(R.string.unknown_error)
                                    }

                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductVerticalGridPreview() {
    FakeShopTheme {
        /*ProductVerticalGrid(

        )*/
    }
}