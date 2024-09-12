package com.evg.product_info.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.evg.ui.theme.blue

@Composable
fun ProductDescription(
    productUI: ProductUI,
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isDescriptionOverflowing by remember { mutableStateOf(false) }

    Column {
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            fontSize = 17.sp,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = productUI.description ?: stringResource(R.string.no_information),
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
                text = if (isDescriptionExpanded) stringResource(R.string.hide) else stringResource(
                    R.string.read_more
                ),
                color = blue
            )
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProductDescriptionPreview() {
    FakeShopTheme {
        ProductDescription(
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
            ),
        )
    }
}