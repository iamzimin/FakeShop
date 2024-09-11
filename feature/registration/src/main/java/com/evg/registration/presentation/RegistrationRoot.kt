package com.evg.registration.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.evg.LocalNavHostController
import com.evg.registration.presentation.viewmodel.RegistrationViewModel

@Composable
fun RegistrationRoot(
    viewModel: RegistrationViewModel = hiltViewModel<RegistrationViewModel>(),
) {
    val navController = LocalNavHostController.current

    RegistrationScreen(
        navController = navController,
        registrationUser = { user, registrationCallback ->
            viewModel.registrationUser(
                user = user,
                registrationCallback = registrationCallback
            )
        }
    )
}