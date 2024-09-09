package com.evg.product_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.SubcomposeAsyncImage
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

    Column(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable {
                Toast.makeText(context, productUI.name, Toast.LENGTH_SHORT).show()
            },
    ) {
        SubcomposeAsyncImage(
            model = productUI.imageURL,
            modifier = Modifier.size(200.dp),
            contentDescription = productUI.imageURL,
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.FillBounds,
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
                /*Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Image(
                        modifier = Modifier
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Error",
                        colorFilter = ColorFilter.tint(Pink80),
                    )
                }*/
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
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = productUI.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = productUI.price,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductTilePreview() {
    FakeShopTheme {
        ProductTile(
            productUI = ProductUI(
                imageURL = "",
                name = "Куртка не куртка или куртка?",
                price = "4000",
                sale = null,
            )
        )
    }
}