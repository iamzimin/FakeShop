package com.evg.product_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.product_list.domain.model.SortType
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.mainDarkColor
import com.evg.ui.theme.mainLightColor

@Composable
fun SortTypeDialog(
    hideDialog: () -> Unit,
    currentOption: SortType,
    selectedOption: (SortType) -> Unit,
    onConfirm: () -> Unit,
) {
    var currentOptionSaved by remember { mutableStateOf(currentOption) }
    val options = listOf(
        SortType.DEFAULT to stringResource(R.string.by_default),
        SortType.ASCENDING to stringResource(R.string.ascending),
        SortType.DESCENDING to stringResource(R.string.descending),
    )

    AlertDialog(
        containerColor = if (isSystemInDarkTheme()) mainDarkColor else mainLightColor,
        onDismissRequest = hideDialog,
        title = { Text(text = stringResource(R.string.sort)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption(option.first)
                                currentOptionSaved = option.first
                            }
                    ) {
                        RadioButton(
                            selected = currentOptionSaved == option.first,
                            onClick = {
                                selectedOption(option.first)
                                currentOptionSaved = option.first
                            }
                        )
                        Text(
                            text = option.second,
                            modifier = Modifier
                                .padding(start = 8.dp)
                        )
                    }
                }
            }

        },
        dismissButton = { },
        confirmButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(BorderRadius),
                onClick = {
                    onConfirm()
                },
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    color = Color.Black,
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SortTypeDialogPreview() {
    FakeShopTheme {
        SortTypeDialog(
            hideDialog = {},
            currentOption = SortType.DEFAULT,
            selectedOption = {},
            onConfirm = {}
        )
    }
}