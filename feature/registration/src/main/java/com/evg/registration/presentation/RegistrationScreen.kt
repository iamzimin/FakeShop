package com.evg.registration.presentation

import android.content.res.Configuration
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.AuthenticationTextField
import com.evg.CustomNavigationBar
import com.evg.fakeshop_api.domain.RegistrationError
import com.evg.registration.domain.model.RegistrationState
import com.evg.resource.R
import com.evg.registration.domain.model.User
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.blue

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    registrationUser: (user: User, callback: (RegistrationState) -> Unit) -> Unit,
) {
    val context = LocalContext.current

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

    val successfulRegistration = stringResource(R.string.successful_registration)

    val errorEmptyName = stringResource(R.string.error_empty_name)
    val errorInvalidEmail = stringResource(R.string.error_invalid_email)
    val errorPasswordMismatch = stringResource(R.string.error_password_mismatch)
    val errorEmptyPassword = stringResource(R.string.error_empty_password)
    val errorPasswordLength = stringResource(R.string.error_password_length)

    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorEmailExists = stringResource(R.string.email_exists)
    val errorUnknown = stringResource(R.string.unknown_error)

    val registrationCallback: (RegistrationState) -> Unit = { registrationState ->
        when (registrationState) {
            is RegistrationState.Error -> {
                val errorMessage = when (registrationState.error) {
                    RegistrationError.REQUEST_TIMEOUT -> errorRequestTimeout
                    RegistrationError.TOO_MANY_REQUESTS -> errorTooManyRequests
                    RegistrationError.SERVER_ERROR -> errorServerError
                    RegistrationError.SERIALIZATION -> errorSerialization
                    RegistrationError.EMAIL_EXIST -> errorEmailExists
                    RegistrationError.PASSWORD_MISMATCH -> errorPasswordMismatch
                    RegistrationError.UNKNOWN -> errorUnknown
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            RegistrationState.Success -> {
                Toast.makeText(context, successfulRegistration, Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            }
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
                text = stringResource(R.string.registration),
            )
            Spacer(modifier = Modifier.height(70.dp))

            val spaceBetweenTextFields = 15.dp
            AuthenticationTextField(
                placeholder = stringResource(R.string.name),
                value = nameText,
                onValueChange = { newText ->
                    nameText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = stringResource(R.string.email),
                value = emailText,
                onValueChange = { newText ->
                    emailText = newText
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = stringResource(R.string.password),
                value = passText,
                onValueChange = { newText ->
                    passText = newText
                },
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(spaceBetweenTextFields))

            AuthenticationTextField(
                placeholder = stringResource(R.string.repeat_password),
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
                text = stringResource(R.string.have_account_enter),
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
                        Toast.makeText(context, errorEmptyName, Toast.LENGTH_SHORT).show()
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) {
                        Toast.makeText(context, errorInvalidEmail, Toast.LENGTH_SHORT).show()
                    } else if (passText.text != pass2Text.text) {
                        Toast.makeText(context, errorPasswordMismatch, Toast.LENGTH_SHORT).show()
                    } else if (passText.text.isEmpty() || pass2Text.text.isEmpty()) {
                        Toast.makeText(context, errorEmptyPassword, Toast.LENGTH_SHORT).show()
                    } else if (passText.text.length >= 24 || pass2Text.text.length >= 24) {
                        Toast.makeText(context, errorPasswordLength, Toast.LENGTH_SHORT).show()
                    } else {
                        registrationUser(
                            User(
                                name = nameText.text,
                                email = emailText.text,
                                password = passText.text,
                                cpassword = pass2Text.text,
                            ),
                            registrationCallback,
                        )
                    }
                }
            ) {
                Text(text = stringResource(R.string.enter))
            }
            Spacer(modifier = Modifier.height(50.dp))
        }

        val profileIndex = 4
        val items = listOf(
            stringResource(R.string.search) to painterResource(R.drawable.avito),
            stringResource(R.string.favorites) to painterResource(R.drawable.favorite),
            stringResource(R.string.ads) to painterResource(R.drawable.list),
            stringResource(R.string.messages) to painterResource(R.drawable.message_bubble),
            stringResource(R.string.profile) to painterResource(R.drawable.user)
        )

        CustomNavigationBar()
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun RegistrationScreenPreview() {
    FakeShopTheme {
        RegistrationScreen(
            navController = NavHostController(LocalContext.current),
            registrationUser = { user, callback -> }
        )
    }
}