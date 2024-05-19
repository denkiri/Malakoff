package com.deletech.malakoff.screens.login.state

import com.deletech.malakoff.R
import com.deletech.malakoff.components.ErrorState

val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_username
)
val usernameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.msg_empty_username
)
val phoneEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.msg_empty_username
)
val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)
val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_confirm_password
)
val projectNameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.project_name_error_msg
)
val projectDescriptionEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.project_description_error_msg
)
val loginErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_user_not_found
)