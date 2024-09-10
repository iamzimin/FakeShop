package com.evg.product_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.SubcomposeAsyncImage
import com.evg.LocalNavHostController
import com.evg.product_list.presentation.model.ProductUI
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.Pink80
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductTile(
    productUI: ProductUI,
) {
    val context = LocalContext.current
    val navController = LocalNavHostController.current

    val imageSize = 160.dp

    Column(
        modifier = Modifier
            .width(imageSize)
            .padding(bottom = 10.dp)
            .clickable {
                navController.navigate("product_info/${productUI.id}")
            },
    ) {
        SubcomposeAsyncImage(
            model = productUI.imageURL,
            modifier = Modifier
                .size(imageSize)
                .clip(RoundedCornerShape(BorderRadius)),
            contentDescription = productUI.imageURL,
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

        Spacer(modifier = Modifier.height(5.dp))


        Column(
            modifier = Modifier
                .height(35.dp)
        ) {
            Text(
                text = productUI.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Column(
            modifier = Modifier.height(50.dp)
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
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductTilePreview() {
    FakeShopTheme {
        ProductTile(
            productUI = ProductUI(
                id = "null",
                imageURL = "",
                name = "Куртка не куртка или куртка куртка?",
                price = "4 000 ₽",
                sale = "3 000 ₽",
                isHaveSale = true
            )
        )
    }
}