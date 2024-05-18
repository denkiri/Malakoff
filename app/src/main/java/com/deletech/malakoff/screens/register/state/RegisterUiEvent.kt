package com.deletech.malakoff.screens.register.state


sealed class RegisterUiEvent {
    data class UsernameChanged(val inputValue: String) : RegisterUiEvent()
    data class PhoneChanged(val inputValue: String) : RegisterUiEvent()
    data class EmailChanged(val inputValue: String) : RegisterUiEvent()
    data class PasswordChanged(val inputValue: String) : RegisterUiEvent()
    data class ConfirmPasswordChanged(val inputValue: String) : RegisterUiEvent()
    object Submit : RegisterUiEvent()
}