package com.deletech.malakoff.screens.splashscreen
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deletech.malakoff.R
import com.deletech.malakoff.screens.login.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController,viewModel: LoginViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchLoginStatus()
    }
    val loginStatusResult by viewModel.loginStatusResultB.collectAsState()
    LaunchedEffect(key1 = true) {
        delay(3000)
        Log.d("loginStatusResponse", "LoginStatus: ${loginStatusResult.data.toString()}")
        if(loginStatusResult.data==true) {
            navController.navigate("login")
            viewModel.resetStates()
        }
        else{
            navController.navigate("login")
            viewModel.resetStates()
        }

    }
    if (isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(
                    Color.Black
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center


        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.playstore),
                contentDescription = "app Logo"
            )

        }
    } else {
        Box(
            modifier = Modifier
                .background(
                  Color.White

                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center


        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.playstore),
                contentDescription = "app Logo"
            )

        }
    }


}
@Composable
@Preview
fun SplashScreenView(){
    SplashScreen(navController = rememberNavController())
}