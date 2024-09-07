package com.evg.registration.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.blue
import com.evg.ui.theme.darkTextFieldBackground
import com.evg.ui.theme.lightTextFieldBackground
import java.time.format.TextStyle

@Composable
fun RegistrationScreen() {
    var nameText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }
    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }
    var passText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }
    var pass2Text by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Регистрация",
            )
            Spacer(modifier = Modifier.height(70.dp))

            val spaceBetweenTextFields = 20.dp
            RegistrationTextField(
                placeholder = "Имя",
                value = nameText,
                onValueChange = { newText ->
                    nameText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            RegistrationTextField(
                placeholder = "Электронная почта",
                value = emailText,
                onValueChange = { newText ->
                    emailText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            RegistrationTextField(
                placeholder = "Пароль",
                value = passText,
                onValueChange = { newText ->
                    passText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            RegistrationTextField(
                placeholder = "Подтвердите пароль",
                value = pass2Text,
                onValueChange = { newText ->
                    pass2Text = newText
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = blue,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {}
            ) {
                Text(text = "Войти")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }

        val profileIndex = 4
        val items = listOf(
            "Поиск" to Icons.Default.Search,
            "Избранное" to Icons.Default.Favorite,
            "Объявления" to Icons.AutoMirrored.Filled.List,
            "Сообщения" to Icons.Default.Call,
            "Профиль" to Icons.Default.Person
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = if (isSystemInDarkTheme()) darkTextFieldBackground else lightTextFieldBackground,
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.second,
                            contentDescription = item.first
                        )
                    },
                    label = {
                        Text(
                            text = item.first,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 9.sp,
                        )
                    },
                    selected = index == profileIndex,
                    onClick = {},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.Unspecified,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color.Unspecified,
                        unselectedTextColor = Color.Unspecified,
                        disabledIconColor = Color.Unspecified,
                        disabledTextColor = Color.Unspecified,
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun RegistrationScreenPreview() {
    FakeShopTheme {
        RegistrationScreen()
    }
}