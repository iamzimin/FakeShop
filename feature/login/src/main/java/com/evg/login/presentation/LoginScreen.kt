package com.evg.login.presentation

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.AuthenticationTextField
import com.evg.CustomNavigationBar
import com.evg.fakeshop_api.domain.utils.LoginError
import com.evg.login.domain.model.LoginState
import com.evg.login.domain.model.User
import com.evg.resource.R
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.FakeShopTheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.lightButtonBackground

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginUser: (user: User, callback: (LoginState) -> Unit) -> Unit,
) {
    val context = LocalContext.current

    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        ) }
    var passText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        ) }

    val loggedAccount = stringResource(R.string.logged_account)

    val errorInvalidEmail = stringResource(R.string.error_invalid_email)

    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorUnknown = stringResource(R.string.unknown_error)
    val errorUserNotFound = stringResource(R.string.user_not_found)

    val loginCallback: (LoginState) -> Unit = { loginState ->
        when (loginState) {
            is LoginState.Error -> {
                val errorMessage = when (loginState.error) {
                    LoginError.REQUEST_TIMEOUT -> errorRequestTimeout
                    LoginError.TOO_MANY_REQUESTS -> errorTooManyRequests
                    LoginError.SERVER_ERROR -> errorServerError
                    LoginError.SERIALIZATION -> errorSerialization
                    LoginError.UNKNOWN -> errorUnknown
                    LoginError.USER_NOT_FOUND -> errorUserNotFound
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            LoginState.Success -> {
                navController.navigate("product_list") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
                Toast.makeText(context, loggedAccount, Toast.LENGTH_SHORT).show()
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
                text = stringResource(R.string.enter),
            )
            Spacer(modifier = Modifier.height(20.dp))

            val spaceBetweenTextFields = 15.dp
            AuthenticationTextField(
                placeholder = stringResource(R.string.phone_or_email),
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

            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .clickable {
                        navController.navigate("registration") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    }
                    .padding(horizontal = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.no_account_register),
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(BorderRadius),
                colors = ButtonColors(
                    containerColor = lightButtonBackground,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                ),
                onClick = {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) {
                        Toast.makeText(context, errorInvalidEmail, Toast.LENGTH_SHORT).show()
                    } else {
                        loginUser(
                            User(
                                email = emailText.text,
                                password = passText.text,
                            ),
                            loginCallback,
                        )
                    }
                }
            ) {
                Text(text = stringResource(R.string.enter))
            }
            Spacer(modifier = Modifier.height(50.dp))
        }

        CustomNavigationBar()
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginScreenPreview() {
    FakeShopTheme {
        LoginScreen(
            navController = NavHostController(LocalContext.current),
            loginUser = { user, callback -> }
        )
    }
}