package com.evg.registration.presentation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.blue
import com.evg.ui.theme.darkTextFieldBackground
import com.evg.ui.theme.lightTextFieldBackground

@Composable
fun RegistrationTextField(
    placeholder: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    val backgroundColor = if (isSystemInDarkTheme()) darkTextFieldBackground else lightTextFieldBackground

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius)),
        singleLine = true,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = Color.Unspecified,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = blue,
        ),
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun RegistrationTextFieldPreview() {
    FakeShopTheme {
        RegistrationTextField(
            placeholder = "Имя",
            value = TextFieldValue(),
            onValueChange = {},
        )
    }
}