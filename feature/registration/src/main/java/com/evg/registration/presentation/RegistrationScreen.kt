package com.evg.registration.presentation

import android.content.res.Configuration
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.AuthenticationTextField
import com.evg.LocalNavHostController
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User
import com.evg.registration.presentation.viewmodel.RegistrationViewModel
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.blue
import com.evg.ui.theme.darkTextFieldBackground
import com.evg.ui.theme.lightTextFieldBackground

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel<RegistrationViewModel>(),
) {
    val context = LocalContext.current
    val navController = LocalNavHostController.current

    var nameText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("nameee")
    ) }
    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("fdsfss435fgsdfge345dfsf@gmail.com")
    ) }
    var passText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("123123123")
    ) }
    var pass2Text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("123123123")
    ) }

    val registrationCallback: (RegistrationStatus) -> Unit = { registrationStatus ->
        if (registrationStatus.status == "success") {
            Toast.makeText(context, "Успешная регистрация", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
        } else if (registrationStatus.status == "fail") {
            Toast.makeText(context, registrationStatus.message?: "Сервер недоступен", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = HorizontalPadding)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Регистрация",
            )
            Spacer(modifier = Modifier.height(70.dp))

            val spaceBetweenTextFields = 15.dp
            AuthenticationTextField(
                placeholder = "Имя",
                value = nameText,
                onValueChange = { newText ->
                    nameText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = "Электронная почта",
                value = emailText,
                onValueChange = { newText ->
                    emailText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = "Пароль",
                value = passText,
                onValueChange = { newText ->
                    passText = newText
                },
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = "Подтвердите пароль",
                value = pass2Text,
                onValueChange = { newText ->
                    pass2Text = newText
                },
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .clickable {
                        navController.navigate("login") {
                            popUpTo("registration") {
                                inclusive = true
                            }
                        }
                    }
                    .padding(horizontal = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = "Есть аккаунт? Войти",
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
                onClick = {
                    if (nameText.text.isEmpty()) {
                        Toast.makeText(context, "Имя не может быть пустым", Toast.LENGTH_SHORT).show()
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) {
                        Toast.makeText(context, "Неверно указан email", Toast.LENGTH_SHORT).show()
                    } else if (passText.text != pass2Text.text) {
                        Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    } else if (passText.text.isEmpty() || pass2Text.text.isEmpty()) {
                        Toast.makeText(context, "Пароль не может быть пустым", Toast.LENGTH_SHORT).show()
                    } else if (passText.text.length >= 24 || pass2Text.text.length >= 24) {
                        Toast.makeText(context, "Максимальная длинна пароля - 24 символа", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.registrationUser(
                            user = User(
                                    name = nameText.text,
                                    email = emailText.text,
                                    password = passText.text,
                                    cpassword = pass2Text.text,
                                ),
                            callback = registrationCallback,
                        )
                    }
                }
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