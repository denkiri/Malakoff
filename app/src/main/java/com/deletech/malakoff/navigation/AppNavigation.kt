package com.deletech.malakoff.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deletech.malakoff.screens.home.HomeScreen
import com.deletech.malakoff.screens.home.HomeViewModel
import com.deletech.malakoff.screens.login.LoginScreen
import com.deletech.malakoff.screens.login.LoginViewModel
import com.deletech.malakoff.screens.register.RegisterScreen
import com.deletech.malakoff.screens.register.RegisterViewModel
import com.deletech.malakoff.screens.splashscreen.SplashScreen


@ExperimentalComposeUiApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            SplashScreen(navController = navController, viewModel = loginViewModel)
        }
        composable("login") {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable("register") {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(navController = navController, viewModel = registerViewModel)
        }
        composable("home_screen") {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

}}
