package com.deletech.malakoff.screens.register
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deletech.malakoff.components.ErrorState
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.models.login.LoginResponse
import com.deletech.malakoff.models.register.RegisterResponse
import com.deletech.malakoff.repository.LoginRepository
import com.deletech.malakoff.screens.login.state.LoginErrorState
import com.deletech.malakoff.screens.login.state.LoginState
import com.deletech.malakoff.screens.login.state.LoginUiEvent
import com.deletech.malakoff.screens.login.state.confirmPasswordEmptyErrorState
import com.deletech.malakoff.screens.login.state.emailEmptyErrorState
import com.deletech.malakoff.screens.login.state.passwordEmptyErrorState
import com.deletech.malakoff.screens.login.state.phoneEmptyErrorState
import com.deletech.malakoff.screens.login.state.usernameEmptyErrorState
import com.deletech.malakoff.screens.register.state.RegisterErrorState
import com.deletech.malakoff.screens.register.state.RegisterState
import com.deletech.malakoff.screens.register.state.RegisterUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel(){
    var registerState = mutableStateOf(RegisterState())
    fun resetStates() {
        registerState.value = RegisterState()
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUiEvent(registerUiEvent: RegisterUiEvent) {
        when (registerUiEvent) {

            is RegisterUiEvent.EmailChanged -> {
                registerState.value = registerState.value.copy(
                    email = registerUiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        emailErrorState = if (registerUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }
            is RegisterUiEvent.UsernameChanged -> {
                registerState.value = registerState.value.copy(
                    username = registerUiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        usernameErrorState = if (registerUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            usernameEmptyErrorState
                    )
                )
            }
            is RegisterUiEvent.PhoneChanged -> {
                registerState.value = registerState.value.copy(
                    phone = registerUiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        phoneErrorState = if (registerUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            phoneEmptyErrorState
                    )
                )
            }



            is RegisterUiEvent.PasswordChanged -> {
                registerState.value = registerState.value.copy(
                    password = registerUiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        passwordErrorState = if (registerUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }
            is RegisterUiEvent.ConfirmPasswordChanged -> {
                registerState.value = registerState.value.copy(
                    confirm_password = registerUiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        confirm_passwordErrorState = if (registerUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is RegisterUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    isLoading
                    // TODO Trigger login in authentication flow
                    registerState.value = registerState.value.copy(isLoginSuccessful = true)


                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val usernameString = registerState.value.username.trim()
        val emailString = registerState.value.email.trim()
        val phoneString = registerState.value.phone.trim()
        val passwordString = registerState.value.password
        val confirmPasswordString =registerState.value.confirm_password
        return when {


            emailString.isEmpty() -> {
                registerState.value = registerState.value.copy(
                    errorState = RegisterErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }
            usernameString.isEmpty() -> {
                registerState.value = registerState.value.copy(
                    errorState = RegisterErrorState(
                        usernameErrorState = emailEmptyErrorState
                    )
                )
                false
            }
            phoneString.isEmpty() -> {
                registerState.value = registerState.value.copy(
                    errorState = RegisterErrorState(
                        phoneErrorState = phoneEmptyErrorState
                    )
                )
                false
            }


            passwordString.isEmpty() -> {
                registerState.value = registerState.value.copy(
                    errorState = RegisterErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }
            confirmPasswordString.isEmpty() -> {
                registerState.value = registerState.value.copy(
                    errorState = RegisterErrorState(
                        confirm_passwordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }
            else -> {
                registerState.value = registerState.value.copy(username = usernameString)

                registerState.value = registerState.value.copy(errorState = RegisterErrorState())
                true
            }
        }
    }
    private val _registerRequestResult = MutableStateFlow<Resource<RegisterResponse>>(Resource.Idle())
    val registerRequestResult: StateFlow<Resource<RegisterResponse>> = _registerRequestResult
    fun register(username:String,
                 email:String,
                 phone:String,
                 password: String,
                 confirm_password:String) {
        viewModelScope.launch {
            _registerRequestResult.value = Resource.Loading()
            try {
                val registerResponse = repository.register(username, email, phone, password, confirm_password)
                if (registerResponse is Resource.Success) {
                    _isLoading.value = false
                    _registerRequestResult.value = Resource.Success(registerResponse.data!!)
                } else {
                    _registerRequestResult.value = Resource.Error(registerResponse.message)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _registerRequestResult.value = Resource.Error("registration failed: ${e.message}")
            }
        }
    }

}