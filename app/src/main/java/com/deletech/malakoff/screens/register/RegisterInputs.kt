package com.deletech.malakoff.screens.register
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.deletech.malakoff.R
import com.deletech.malakoff.components.EmailTextField
import com.deletech.malakoff.components.PasswordTextField
import com.deletech.malakoff.components.PhoneTextField
import com.deletech.malakoff.components.UsernameTextField
import com.deletech.malakoff.screens.register.state.RegisterState
import com.deletech.malakoff.ui.theme.AppTheme


@Composable
fun RegisterInputs(
    registerState: RegisterState,
    onUsernameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,

    ) {

    Column(modifier = Modifier.fillMaxWidth()) {

        UsernameTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value =  registerState.username,
            onValueChange = onUsernameChange,
            label = stringResource(id = R.string.user),
            isError =  registerState.errorState.usernameErrorState.hasError,
            errorText = stringResource(id = registerState.errorState.usernameErrorState.errorMessageStringResource)
        )
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value =  registerState.email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.email),
            isError =  registerState.errorState.emailErrorState.hasError,
            errorText = stringResource(id = registerState.errorState.emailErrorState.errorMessageStringResource)
        )
        PhoneTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value =  registerState.phone,
            onValueChange = onPhoneChange,
            label = stringResource(id = R.string.phone),
            isError =  registerState.errorState.phoneErrorState.hasError,
            errorText = stringResource(id = registerState.errorState.phoneErrorState.errorMessageStringResource)
        )
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registerState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            isError = registerState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = registerState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registerState.confirm_password,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(id = R.string.confirm_password_label),
            isError = registerState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = registerState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )





    }
}