package com.deletech.malakoff.screens.register
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
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
import com.deletech.malakoff.screens.register.state.RegisterUiEvent
import com.deletech.malakoff.ui.theme.AppTheme

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: RegisterViewModel = hiltViewModel()){
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val registerState by remember {
        viewModel.registerState
    }
    val authState by viewModel.registerRequestResult.collectAsState()
    LaunchedEffect(authState) {
        if (authState is Resource.Success && authState.data?.data != null) {
             navController.navigate("login")
            viewModel.resetStates()
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
                        RegisterInputs(
                            registerState = registerState,
                            onUsernameChange = { inputString ->
                                viewModel.onUiEvent(
                                    registerUiEvent = RegisterUiEvent.UsernameChanged(
                                        inputString
                                    )
                                )
                            },
                            onPhoneChange = { inputString ->
                                viewModel.onUiEvent(
                                    registerUiEvent = RegisterUiEvent.PhoneChanged(
                                        inputString
                                    )
                                )
                            },
                            onEmailChange = { inputString ->
                                viewModel.onUiEvent(
                                    registerUiEvent = RegisterUiEvent.EmailChanged(
                                        inputString
                                    )
                                )
                            },
                            onPasswordChange = { inputString ->
                                viewModel.onUiEvent(
                                    registerUiEvent = RegisterUiEvent.PasswordChanged(
                                        inputString
                                    )
                                )
                            },
                            onConfirmPasswordChange = { inputString ->
                                viewModel.onUiEvent(
                                    registerUiEvent = RegisterUiEvent.ConfirmPasswordChanged(
                                        inputString
                                    )
                                )
                            },
                        )
                        NormalButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.register_button_text),
                            onClick = {
                                viewModel.onUiEvent(registerUiEvent = RegisterUiEvent.Submit)
                                if (registerState.isLoginSuccessful) {
                                    viewModel.register(viewModel.registerState.value.username.trim(),
                                        viewModel.registerState.value.email.trim(),
                                        viewModel.registerState.value.phone.trim(),
                                        viewModel.registerState.value.password.trim(),
                                    viewModel.registerState.value.confirm_password.trim())

                                }
                            }
                        )





                    }


                }

            }
        }
    }
}



