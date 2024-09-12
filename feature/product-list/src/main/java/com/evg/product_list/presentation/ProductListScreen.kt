package com.evg.product_list.presentation

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.resource.R
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.model.Specification
import com.evg.product_list.presentation.mapper.toProductUI
import com.evg.product_list.presentation.model.AuthenticateState
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.product_list.presentation.model.ProductState
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.lightButtonBackground
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListScreen(
    products: LazyPagingItems<ProductState>,
    sortType: () -> SortType,
    setSortType: (SortType) -> Unit,
    setCategoryFilter: (String?) -> Unit,
    setCategoryPageSize: (Int) -> Unit,
    updateProducts: () -> Unit,
    navController: NavHostController,
    isAuthenticateLoading: Boolean,
    authenticateUser: (authenticateCallback: (AuthenticateState) -> Unit) -> Unit,
) {
    val tabList = listOf(
        stringResource(R.string.recommendations),
        stringResource(R.string.latest),
        stringResource(R.string.nearby)
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabList.size })
    val coroutineScope = rememberCoroutineScope()

    /*var isInitialized by rememberSaveable { mutableStateOf(false) }

    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorUnknown = stringResource(R.string.unknown_error)

    if (!isInitialized) {
        LaunchedEffect(Unit) {
            authenticateUser { state ->
                when (state) {
                    is AuthenticateState.Success -> {}
                    is AuthenticateState.Error -> {
                        val errorMessage = when (state.error) {
                            NetworkError.REQUEST_TIMEOUT -> errorRequestTimeout
                            NetworkError.TOO_MANY_REQUESTS -> errorTooManyRequests
                            NetworkError.SERVER_ERROR -> errorServerError
                            NetworkError.SERIALIZATION -> errorSerialization
                            NetworkError.UNKNOWN -> errorUnknown
                        }
                        navController.navigate("login") {
                            popUpTo("product_list") {
                                inclusive = true
                            }
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            isInitialized = true
        }
    }

    if (isAuthenticateLoading) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
        return
    }*/


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = HorizontalPadding, end = HorizontalPadding),
    ) {
        CategoryScroll(
            setCategoryFilter = setCategoryFilter,
        )

        Spacer(modifier = Modifier.height(30.dp))

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            indicator = {},
            divider = {},
        ) {
            tabList.forEachIndexed { index, text ->
                val selected = pagerState.currentPage == index
                Tab(
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    text = {
                        Text(
                            text = text,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected) MaterialTheme.colorScheme.onSurface else Color.Gray,
                            style = if (selected) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) {}

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            ProductVerticalGrid(
                updateProducts = updateProducts,
                products = products,
            )
        }

        BottomButtons(
            setCategoryPageSize = setCategoryPageSize,
            sortType = {
                sortType()
            },
            setSortType = { sortType ->
                setSortType(
                    sortType
                )
            },
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductListScreenPreview() {
    FakeShopTheme {
        val products = List(10) {
            Product(
                id = "1",
                name = "Product 1",
                category = listOf("Category 1"),
                price = 1000,
                discountedPrice = 900,
                images = listOf("1.jpg", "2.jpg"),
                productRating = 4.5,
                brand = "Brand 1",
                productSpecifications = listOf(
                    Specification(key = "Color", value = "Red"),
                    Specification(key = "Size", value = "M")
                )
            )
        }

        ProductListScreen(
            products = flowOf(PagingData.from(emptyList<ProductState>())).collectAsLazyPagingItems(),
            navController = NavHostController(LocalContext.current),
            sortType = { SortType.DEFAULT },
            setSortType = {},
            setCategoryFilter = {},
            updateProducts = {},
            setCategoryPageSize = {},
            isAuthenticateLoading = true,
            authenticateUser = {},
        )
    }
}