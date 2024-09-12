package com.evg.product_info.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.evg.product_info.presentation.model.ProductUI
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.Pink80
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductImagesScroll(
    listState: LazyListState,
    productUI: ProductUI,
    visibleIndex: Int
) {
    Box {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        ) {
            if (productUI.imageURL.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(BorderRadius)),
                    ) {
                        ImageError()
                    }
                }
            }
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
                            ImageError()
                        },
                    )
                }
            }
        }
        if (productUI.imageURL.isNotEmpty()) {
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
}

@Composable
private fun ImageError() {
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
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductImagesScrollPreview() {
    FakeShopTheme {
        /*ProductInfoScreen(

        )*/
    }
}