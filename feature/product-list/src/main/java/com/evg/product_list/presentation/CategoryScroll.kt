package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.product_list.presentation.model.CategoryUI
import com.evg.resource.R
import com.evg.ui.theme.FakeShopTheme

@Composable
fun CategoryScroll(
    setCategoryFilter: (String?) -> Unit,
) {
    var selectedTile: String? by rememberSaveable { mutableStateOf(null) }

    val firstCategoryRow = listOf(
        CategoryUI(title = stringResource(R.string.automotive), originalName = "automotive"),
        CategoryUI(title = stringResource(R.string.bags), originalName = "bags"),
        CategoryUI(title = stringResource(R.string.footwear), originalName = "footwear"),
        CategoryUI(title = stringResource(R.string.shampoo), originalName = "shampoo"),
        CategoryUI(title = stringResource(R.string.shorts), originalName = "shorts"),
    )

    val secondCategoryRow = listOf(
        CategoryUI(title = stringResource(R.string.table), originalName = "table"),
        CategoryUI(title = stringResource(R.string.tools), originalName = "tools"),
        CategoryUI(title = stringResource(R.string.furniture), originalName = "furniture"),
        CategoryUI(title = stringResource(R.string.hardware), originalName = "hardware"),
        CategoryUI(title = stringResource(R.string.lamp), originalName = "lamp"),
    )

    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = firstCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = firstCategoryRow[index],
                    isSelected = selectedTile == firstCategoryRow[index].originalName,
                    onTileClick = { selectedCategory ->
                        setCategoryFilter(selectedCategory)
                        selectedTile = selectedCategory
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = secondCategoryRow.size,
            ) { index ->
                CategoryTile(
                    categoryUI = secondCategoryRow[index],
                    isSelected = selectedTile == secondCategoryRow[index].originalName,
                    onTileClick = { selectedCategory ->
                        setCategoryFilter(selectedCategory)
                        selectedTile = selectedCategory
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CategoryScrollPreview() {
    FakeShopTheme {
        /*CategoryScroll(

        )*/
    }
}