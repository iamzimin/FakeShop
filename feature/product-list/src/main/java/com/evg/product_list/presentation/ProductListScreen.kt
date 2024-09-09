package com.evg.product_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.evg.product_list.domain.mapper.toProductUI
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.product_list.presentation.model.ProductUI
import com.evg.product_list.presentation.viewmodel.ProductListViewModel
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.blue
import com.evg.ui.theme.lightButtonBackground
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel<ProductListViewModel>(),
) {
    val products = viewModel.products.collectAsLazyPagingItems()
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)


    val firstCategoryRow = List(5) {
        CategoryUI(
            title = "Недвижимость",
            image = Icons.Filled.Person,
            width = 200,
            originalName = "Auto",
        )
    }

    val tabList = listOf("Рекомендации", "Свежее", "Рядом с вами")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabList.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = firstCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = firstCategoryRow[index],
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = firstCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = firstCategoryRow[index],
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

        Box {
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
                            text = "Products loading error. Swipe to refresh",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                is LoadState.NotLoading -> {
                    SwipeRefresh(
                        state = refreshingState,
                        onRefresh = { viewModel.updateProducts() },
                        indicator = { state, trigger ->
                            SwipeRefreshIndicator(
                                state = state,
                                refreshTriggerDistance = trigger,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.primary,
                            )
                        },
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            horizontalArrangement = Arrangement.spacedBy(30.dp),
                        ) {
                            items(
                                count = products.itemCount,
                                key = products.itemKey { it.id },
                            ) { index ->
                                val item = products[index]
                                if (item != null) {
                                    ProductTile(
                                        productUI = item.toProductUI()
                                    )
                                }
                            }
                        }
                    }
                }
            }



            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
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
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(horizontal = 10.dp),
                ) {
                    Text(
                        text = "Показать по",
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
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
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
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductListScreenPreview() {
    FakeShopTheme {
        ProductListScreen()
    }
}