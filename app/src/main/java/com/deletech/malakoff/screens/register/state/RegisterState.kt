package com.deletech.malakoff.screens.register.state
import com.deletech.malakoff.components.ErrorState
import com.deletech.malakoff.screens.login.state.LoginErrorState

data class RegisterState(
    val username: String = "",
    val email:String ="",
    val phone:String ="",
    val password: String = "",
    val confirm_password:String ="",
    val errorState: RegisterErrorState = RegisterErrorState(),
    val isLoginSuccessful: Boolean = false
)

data class RegisterErrorState(
    val usernameErrorState: ErrorState = ErrorState(),
    val emailErrorState: ErrorState = ErrorState(),
    val phoneErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirm_passwordErrorState: ErrorState = ErrorState()
)

