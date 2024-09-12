package com.evg.product_info.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.product_info.presentation.model.ProductUI
import com.evg.product_info.presentation.model.Spec
import com.evg.product_info.presentation.model.SpecificationUI
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.Pink80
import com.evg.ui.theme.blue
import com.evg.ui.theme.darkSpecText
import com.evg.ui.theme.lightSpecText
import com.evg.ui.theme.purple
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductInfoScreen(
    productId: String,
    productUI: ProductUI?,
    getProductInfo: (id: String, productCallback: (NetworkError) -> Unit) -> Unit,
    isProductLoading: Boolean,
) {
    val context = LocalContext.current

    var isInitialized by rememberSaveable { mutableStateOf(false) }

    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isDescriptionOverflowing by remember { mutableStateOf(false) }
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)

    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorUnknown = stringResource(R.string.unknown_error)

    val loadProductInfo: () -> Unit = {
        getProductInfo(productId) { error ->
            val errorType = when (error) {
                NetworkError.REQUEST_TIMEOUT -> errorRequestTimeout
                NetworkError.TOO_MANY_REQUESTS -> errorTooManyRequests
                NetworkError.SERVER_ERROR -> errorServerError
                NetworkError.SERIALIZATION -> errorSerialization
                NetworkError.UNKNOWN -> errorUnknown
            }
            Toast.makeText(context, errorType, Toast.LENGTH_SHORT).show()
        }
    }

    if (!isInitialized) {
        LaunchedEffect(productId) {
            loadProductInfo()
            isInitialized = true
        }
    }

    if (isProductLoading) {
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
    } else if (productUI == null) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = refreshingState,
            onRefresh = { loadProductInfo() },
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
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.product_loading_error),
                    textAlign = TextAlign.Center,
                )
            }
        }
    } else {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = refreshingState,
            onRefresh = { loadProductInfo() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            },
        ) {
            val listState = rememberLazyListState()
            var visibleIndex by remember { mutableIntStateOf(0) }
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }
                    .collect {
                        visibleIndex = it
                    }
            }

            Column(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ProductImagesScroll(
                    listState = listState,
                    productUI = productUI,
                    visibleIndex = visibleIndex,
                )


                Spacer(modifier = Modifier.height(20.dp))


                Column(
                    modifier = Modifier
                        .padding(horizontal = HorizontalPadding)
                ) {
                    if (productUI.isHaveSale) {
                        Text(
                            text = productUI.sale,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = productUI.price,
                            style = MaterialTheme.typography.titleSmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        Text(
                            text = productUI.price,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }


                    Spacer(modifier = Modifier.height(20.dp))


                    Text(
                        text = productUI.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Specifications(
                        productUI = productUI
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    ProductDescription(
                        productUI = productUI,
                        isDescriptionExpanded = isDescriptionExpanded,
                        isDescriptionOverflowing = isDescriptionOverflowing,
                    )

                    Spacer(modifier = Modifier.height(120.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = HorizontalPadding,
                        end = HorizontalPadding,
                        bottom = 40.dp
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { },
                    shape = RoundedCornerShape(BorderRadius),
                    colors = ButtonColors(
                        containerColor = purple,
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified,
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.buy),
                        color = Color.White,
                    )
                }
            }
        }

    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductInfoScreenPreview() {
    FakeShopTheme {
        ProductInfoScreen(
            productId = "null",
            isProductLoading = false,
            getProductInfo = { _, _ -> },
            productUI = ProductUI(
                id = "",
                imageURL = List(1) {""},
                name = "Some kind of product",
                description = "Furniture, furniture, furniture, furniture, furniture, furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture, Furniture",
                price = "5 000 ₽",
                sale = "4 000 ₽",
                isHaveSale = true,
                productSpecifications = listOf(
                    SpecificationUI(
                        key = Spec.CATEGORY,
                        value = "Furniture"
                    ),
                    SpecificationUI(
                        key = Spec.CONDITION,
                        value = null
                    ),
                    SpecificationUI(
                        key = Spec.SIZE,
                        value = "Large"
                    ),
                    SpecificationUI(
                        key = Spec.FABRIC,
                        value = "Cotton"
                    ),
                    SpecificationUI(
                        key = Spec.BRAND,
                        value = "No Name"
                    ),
                    SpecificationUI(
                        key = Spec.COLOR,
                        value = "Black"
                    ),
                )
            )
        )
    }
}