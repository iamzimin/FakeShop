package com.evg.fakeshop

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.evg.LocalNavHostController
import com.evg.login.presentation.LoginRoot
import com.evg.login.presentation.LoginScreen
import com.evg.product_info.presentation.ProductInfoRoot
import com.evg.product_list.presentation.ProductListRoot
import com.evg.product_list.presentation.ProductListScreen
import com.evg.registration.presentation.RegistrationRoot
import com.evg.registration.presentation.RegistrationScreen
import com.evg.shared_prefs.data.repository.SharedPrefsRepositoryImpl
import com.evg.ui.theme.FakeShopTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sharedPreferencesRepository = SharedPrefsRepositoryImpl(context = LocalContext.current)
    val isUserAuthenticated: Boolean = sharedPreferencesRepository.getUserToken() != null

    val startDestination = if (isUserAuthenticated) {
        "product_list"
    } else {
        "registration"
    }

    CompositionLocalProvider(LocalNavHostController provides navController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                ) {
                    composable(
                        route = "registration"
                    ) {
                        RegistrationRoot()
                    }

                    composable( route = "login") {
                        LoginRoot()
                    }

                    composable(
                        route = "product_list"
                    ) {
                        ProductListRoot()
                    }

                    composable(
                        route = "product_info/{id}",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "https://fakeshopapi-l2ng.onrender.com/app/v1/products/{id}"
                                action = Intent.ACTION_VIEW
                            },
                        ),
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.StringType
                                defaultValue = "null"
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getString("id") ?: "null"
                        ProductInfoRoot(
                            productId = id
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MainScreenPreview() {
    FakeShopTheme {
        MainScreen()
    }
}