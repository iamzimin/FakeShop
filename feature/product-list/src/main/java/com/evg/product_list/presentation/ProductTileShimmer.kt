package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductTileShimmer() {
    val imageSize = 160.dp
    val shimmerColor = Color.Gray

    Column(
        modifier = Modifier
            .width(imageSize)
            .height(300.dp),
    ) {
        Box(
            modifier = Modifier
                .size(imageSize)
                .shimmer()
                .clip(RoundedCornerShape(BorderRadius)),
        ) {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .background(shimmerColor)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.shimmer(),
        ) {
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(imageSize)
                    .clip(shape = RoundedCornerShape(BorderRadius))
                    .background(shimmerColor)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.shimmer(),
        ) {
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(imageSize)
                    .clip(shape = RoundedCornerShape(BorderRadius))
                    .background(shimmerColor)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier.height(50.dp)
        ) {
            Box(
                modifier = Modifier.shimmer(),
            ) {
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .width(70.dp)
                        .clip(shape = RoundedCornerShape(BorderRadius))
                        .background(shimmerColor)
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Box(
                modifier = Modifier.shimmer(),
            ) {
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(80.dp)
                        .clip(shape = RoundedCornerShape(BorderRadius))
                        .background(shimmerColor)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductTileShimmerPreview() {
    FakeShopTheme {
        ProductTileShimmer()
    }
}