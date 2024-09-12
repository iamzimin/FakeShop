package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.product_list.domain.model.SortType
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.lightButtonBackground

@Composable
fun BottomButtons(
    setCategoryPageSize: (Int) -> Unit,
    sortType: () -> SortType,
    setSortType: (SortType) -> Unit,
) {
    var isShowDialog by remember { mutableStateOf(false) }
    var currentOption by remember { mutableStateOf(sortType()) }

    if (isShowDialog) {
        SortTypeDialog(
            hideDialog = { isShowDialog = false },
            currentOption = sortType(),
            selectedOption = {
                currentOption = it
            },
            onConfirm = {
                setSortType(currentOption)
                isShowDialog = false
            },
        )
    }


    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 45.dp)
    ) {
        val buttonSize = 40.dp

        Button(
            modifier = Modifier.height(buttonSize),
            shape = RoundedCornerShape(BorderRadius),
            colors = ButtonColors(
                containerColor = lightButtonBackground,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            ),
            onClick = {
                isShowDialog = true
            },
            contentPadding = PaddingValues(horizontal = 10.dp),
        ) {
            Text(
                text = stringResource(R.string.show_by),
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(BorderRadius),
            colors = ButtonColors(
                containerColor = lightButtonBackground,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            ),
            onClick = {
                setCategoryPageSize(10)
            },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "10",
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(BorderRadius),
            colors = ButtonColors(
                containerColor = lightButtonBackground,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            ),
            onClick = {
                setCategoryPageSize(20)
            },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "20",
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(BorderRadius),
            colors = ButtonColors(
                containerColor = lightButtonBackground,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            ),
            onClick = {
                setCategoryPageSize(30)
            },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "30",
                color = Color.Black,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun BottomButtonsPreview() {
    FakeShopTheme {
        /*BottomButtons(

        )*/
    }
}