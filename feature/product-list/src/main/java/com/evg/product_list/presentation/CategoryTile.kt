package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.darkTextFieldBackground
import com.evg.ui.theme.lightTextFieldBackground

@Composable
fun CategoryTile(
    categoryUI: CategoryUI,
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .width(categoryUI.width.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = if (isSystemInDarkTheme()) darkTextFieldBackground else lightTextFieldBackground)
            .clickable {

            }
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = categoryUI.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        /*Image(
            painter = ,
            contentDescription = ,
        )*/
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CategoryTilePreview() {
    FakeShopTheme {
        CategoryTile(
            categoryUI = CategoryUI(
                title = "Авто",
                image = Icons.Filled.Person,
                width = 200,
                originalName = "Auto",
            ),
        )
    }
}