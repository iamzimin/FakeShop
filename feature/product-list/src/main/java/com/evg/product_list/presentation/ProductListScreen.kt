package com.evg.product_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.product_list.presentation.model.ProductState
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.lightButtonBackground
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
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
) {
    val context = LocalContext.current
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)
    var selectedTile: String? by rememberSaveable { mutableStateOf(null) }

    val firstCategoryRow = listOf(
        CategoryUI(title = stringResource(R.string.automotive), originalName = "automotive"),
        CategoryUI(title = stringResource(R.string.bags), originalName = "bags"),
        CategoryUI(title = stringResource(R.string.footwear), originalName = "footwear"),
        CategoryUI(title = stringResource(R.string.shampoo), originalName = "shampoo"),
        CategoryUI(title = stringResource(R.string.shorts), originalName = "shorts"),
    )

    val secondCategoryRow = listOf(
        CategoryUI(title = stringResource(R.string.table), originalName = "table"),
        CategoryUI(title = stringResource(R.string.tools), originalName = "tools"),
        CategoryUI(title = stringResource(R.string.furniture), originalName = "furniture"),
        CategoryUI(title = stringResource(R.string.hardware), originalName = "hardware"),
        CategoryUI(title = stringResource(R.string.lamp), originalName = "lamp"),
    )

    val tabList = listOf(
        stringResource(R.string.recommendations),
        stringResource(R.string.latest),
        stringResource(R.string.nearby)
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabList.size })
    val coroutineScope = rememberCoroutineScope()

    var isShowDialog by remember { mutableStateOf(false) }
    var currentOption by remember { mutableStateOf(sortType()) }

    if (isShowDialog) {
        SortTypeDialog(
            hideDialog = { isShowDialog = false },
            currentOption = sortType(),
            selectedOption = {
                currentOption = it
            },
            onConfirm = {
                setSortType(currentOption)
                isShowDialog = false
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = HorizontalPadding, end = HorizontalPadding),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = firstCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = firstCategoryRow[index],
                    isSelected = selectedTile == firstCategoryRow[index].originalName,
                    onTileClick = { selectedCategory ->
                        setCategoryFilter(selectedCategory)
                        selectedTile = selectedCategory
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = secondCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = secondCategoryRow[index],
                    isSelected = selectedTile == secondCategoryRow[index].originalName,
                    onTileClick = { selectedCategory ->
                        setCategoryFilter(selectedCategory)
                        selectedTile = selectedCategory
                    }
                )
            }
        }

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
            when (products.loadState.refresh) {
                is LoadState.Loading -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                    )  {
                        items(10) {
                            ProductTileShimmer()
                        }
                    }
                }
                is LoadState.Error -> {
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

                        // TODO products.size - 0
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

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {
            val buttonSize = 40.dp

            Button(
                modifier = Modifier.height(buttonSize),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = lightButtonBackground,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {
                    isShowDialog = true
                },
                contentPadding = PaddingValues(horizontal = 10.dp),
            ) {
                Text(
                    text = stringResource(R.string.show_by),
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = lightButtonBackground,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {
                    setCategoryPageSize(10)
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "10",
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = lightButtonBackground,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {
                    setCategoryPageSize(20)
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "20",
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = lightButtonBackground,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {
                    setCategoryPageSize(30)
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "30",
                    color = Color.Black,
                )
            }
        }
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
            sortType = { SortType.DEFAULT },
            setSortType = {},
            setCategoryFilter = {},
            updateProducts = {},
            setCategoryPageSize = {},
        )
    }
}