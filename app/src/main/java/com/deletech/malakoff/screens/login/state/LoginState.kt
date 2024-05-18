package com.deletech.malakoff.screens.login.state
import com.deletech.malakoff.components.ErrorState

data class LoginState(
    val username: String = "",
    val password: String = "",
    val errorState: LoginErrorState = LoginErrorState(),
    val isLoginSuccessful: Boolean = false
)

data class LoginErrorState(
    val usernameErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState()
)

