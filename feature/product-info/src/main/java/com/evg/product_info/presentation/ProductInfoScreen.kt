package com.evg.product_info.presentation

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
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
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductInfoScreen(
    productUI: ProductUI?,
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isDescriptionOverflowing by remember { mutableStateOf(false) }

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
            .padding(top = 50.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (productUI != null) {
            Box {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = listState,
                    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                ) {
                    itemsIndexed(productUI.imageURL) { _, image ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            SubcomposeAsyncImage(
                                model = image,
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(RoundedCornerShape(BorderRadius)),
                                contentDescription = image,
                                alignment = Alignment.CenterStart,
                                contentScale = ContentScale.Crop,
                                loading = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(shape = RoundedCornerShape(BorderRadius))
                                            .shimmer(),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.LightGray)
                                        )
                                    }
                                },
                                error = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(shape = RoundedCornerShape(BorderRadius))
                                            .border(
                                                0.5.dp,
                                                MaterialTheme.colorScheme.onSurface,
                                                RoundedCornerShape(BorderRadius)
                                            )
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .size(70.dp)
                                                .align(Alignment.Center),
                                            painter = painterResource(id = R.drawable.error),
                                            contentDescription = "Error",
                                            colorFilter = ColorFilter.tint(Pink80),
                                        )
                                    }
                                },
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                        .background(
                            color = Color(0xFF726E68).copy(alpha = 0.8f),
                            RoundedCornerShape(BorderRadius)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "${visibleIndex + 1}-${productUI.imageURL.size}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))


        Column(
            modifier = Modifier
                .padding(horizontal = HorizontalPadding)
        ) {
            if (productUI == null) {

            } else {
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

                Text(
                    text = stringResource(R.string.specifications),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                )

                Spacer(modifier = Modifier.height(5.dp))


                productUI.productSpecifications.forEach { spec ->
                    Row {
                        val title = when (spec.key) {
                            Spec.CATEGORY -> stringResource(R.string.category)
                            Spec.CONDITION -> stringResource(R.string.condition)
                            Spec.SIZE -> stringResource(R.string.size)
                            Spec.FABRIC -> stringResource(R.string.fabric)
                            Spec.BRAND -> stringResource(R.string.brand)
                            Spec.COLOR -> stringResource(R.string.color)
                        }

                        Text(
                            text = "${title}:",
                            style = MaterialTheme.typography.titleSmall,
                            color = if (isSystemInDarkTheme()) darkSpecText else lightSpecText
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = spec.value ?: stringResource(R.string.no_information),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = productUI.description,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isDescriptionExpanded) 40 else 4,
                    onTextLayout = { textLayoutResult ->
                        isDescriptionOverflowing = textLayoutResult.hasVisualOverflow
                    }
                )
                if (isDescriptionOverflowing) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(RoundedCornerShape(1.dp))
                            .clickable {
                                isDescriptionExpanded = !isDescriptionExpanded
                            },
                        style = MaterialTheme.typography.titleSmall,
                        text = if (isDescriptionExpanded) stringResource(R.string.hide) else stringResource(R.string.read_more),
                        color = blue
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(HorizontalPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { },
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

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductInfoScreenPreview() {
    FakeShopTheme {
        ProductInfoScreen(
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