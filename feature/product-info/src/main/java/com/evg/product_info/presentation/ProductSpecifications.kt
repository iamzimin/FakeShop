package com.evg.product_info.presentation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.product_info.presentation.model.ProductUI
import com.evg.product_info.presentation.model.Spec
import com.evg.product_info.presentation.model.SpecificationUI
import com.evg.resource.R
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.darkSpecText
import com.evg.ui.theme.lightSpecText

@Composable
fun Specifications(
    productUI: ProductUI
) {
    Column {
        Text(
            text = stringResource(R.string.specifications),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            fontSize = 17.sp,
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SpecificationsPreview() {
    FakeShopTheme {
        Specifications(
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