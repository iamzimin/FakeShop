package com.evg.login.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.LocalNavHostController
import com.evg.login.presentation.viewmodel.LoginViewModel

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
) {
    val navController = LocalNavHostController.current

    LoginScreen(
        navController = navController,
        loginUser = { user, loginCallback ->
            viewModel.loginUser(
                user = user,
                loginCallback = loginCallback
            )
        }
    )
}