package com.deletech.malakoff.screens.login
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.deletech.malakoff.R
import com.deletech.malakoff.components.Loader
import com.deletech.malakoff.components.NormalButton
import com.deletech.malakoff.components.Toast
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.screens.login.state.LoginUiEvent
import com.deletech.malakoff.storage.PreferenceManager
import com.deletech.malakoff.ui.theme.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext


@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val loginState by remember {
        viewModel.loginState
    }
    val context = LocalContext.current

    val authState by viewModel.loginRequestResult.collectAsState()
    LaunchedEffect(authState) {
        if (authState is Resource.Success && authState.data?.data != null) {
            PreferenceManager(context).setAccessToken(authState.data!!.data.access_token)
            viewModel.updateLoginStatus(true)
            navController.navigate("home_screen")
            viewModel.resetStates()
            Log.d("loginDataResponse", "ProfileData: ${authState.data}")
        }
    }
    when (authState) {
        is Resource.Idle -> {
        }
        is Resource.Loading -> {
        Loader()
            Toast(message = "Loading...Please wait")
        }
        is Resource.Success -> {
            if (authState.data != null) {
                Toast(message = authState.data?.message.toString())
            }
        }
        is Resource.Error -> {
            Toast(message = authState.message.toString())

        }

        else -> {}
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor)
    ) {
        // Background image
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .imePadding()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                        Column(
                            modifier = Modifier
                                .padding(horizontal = AppTheme.dimens.paddingLarge)
                                .padding(bottom = AppTheme.dimens.paddingExtraLarge)
                        ) {

                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)
                                    .padding(top = AppTheme.dimens.paddingSmall),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(data = R.drawable.playstore)
                                    .crossfade(enable = true)
                                    .scale(Scale.FILL)
                                    .build(),
                                contentDescription = stringResource(id = R.string.login_heading_text)
                            )
                            LoginInputs(
                                loginState = loginState,
                                onEmailChange = { inputString ->
                                    viewModel.onUiEvent(
                                        loginUiEvent = LoginUiEvent.EmailChanged(
                                            inputString
                                        )
                                    )
                                },
                                onPasswordChange = { inputString ->
                                    viewModel.onUiEvent(
                                        loginUiEvent = LoginUiEvent.PasswordChanged(
                                            inputString
                                        )
                                    )
                                },
                            )
                            NormalButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.login_button_text),
                                onClick = {
                                    viewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                                    if (loginState.isLoginSuccessful) {
                                        viewModel.login(viewModel.loginState.value.username.trim(),
                                            viewModel.loginState.value.password.trim())

                                    }
                                }
                            )
                            Row(
                                modifier = Modifier
                                    .padding(AppTheme.dimens.paddingNormal)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    modifier = Modifier
                                        .padding(start = AppTheme.dimens.paddingExtraSmall)
                                        .clickable {
                                            navController.navigate("register")
                                        },
                                    text = stringResource(id = R.string.register_button_text),
                                    color = MaterialTheme.colorScheme.primary
                                )

                            }




                        }


                }

            }
        }
    }
}
fun Context.showToast(message: String) {
    android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
}


