package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.mainDarkColor
import com.evg.ui.theme.mainLightColor

@Composable
fun CategoryTile(
    categoryUI: CategoryUI,
    isSelected: Boolean,
    onTileClick: (String?) -> Unit,
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = if (isSystemInDarkTheme()) mainDarkColor else mainLightColor)
            .clickable {
                if (isSelected) {
                    onTileClick(null)
                } else {
                    onTileClick(categoryUI.originalName)
                }
            }
            .then(
                if (isSelected) {
                    Modifier.border(0.5.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(BorderRadius))
                } else {
                    Modifier
                }
            )

    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .padding(end = 70.dp),
            text = categoryUI.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CategoryTilePreview() {
    FakeShopTheme {
        CategoryTile(
            categoryUI = CategoryUI(
                title = "Auto",
                originalName = "Auto",
            ),
            isSelected = true,
            onTileClick = {}
        )
    }
}