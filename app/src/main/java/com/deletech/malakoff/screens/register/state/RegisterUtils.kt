package com.deletech.malakoff.screens.register.state

import com.deletech.malakoff.R
import com.deletech.malakoff.components.ErrorState

val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_username
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)
val loginErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_user_not_found
)