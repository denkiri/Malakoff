package com.deletech.malakoff.screens.login
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
import com.deletech.malakoff.screens.login.state.emailEmptyErrorState
import com.deletech.malakoff.screens.login.state.passwordEmptyErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel(){
    var loginState = mutableStateOf(LoginState())
    fun resetStates() {
        loginState.value = LoginState()
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.EmailChanged -> {
                loginState.value = loginState.value.copy(
                    username = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        usernameErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    isLoading
                    // TODO Trigger login in authentication flow
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)


                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailOrMobileString = loginState.value.username.trim()
        val passwordString = loginState.value.password
        return when {

            emailOrMobileString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        usernameErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            else -> {
                loginState.value = loginState.value.copy(username = emailOrMobileString)

                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }
    private val _loginRequestResult = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle())
    val loginRequestResult: StateFlow<Resource<LoginResponse>> = _loginRequestResult
    fun login(mobileNumber: String, password: String) {
        viewModelScope.launch {
            _loginRequestResult.value = Resource.Loading()
            try {
                val loginResponse = repository.login(mobileNumber, password)
                if (loginResponse is Resource.Success) {
                    _isLoading.value = false
                    _loginRequestResult.value = Resource.Success(loginResponse.data!!)
                } else {
                    _loginRequestResult.value = Resource.Error(loginResponse.message)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _loginRequestResult.value = Resource.Error("Login failed: ${e.message}")
            }
        }
    }
    private val _registerRequestResult = MutableStateFlow<Resource<RegisterResponse>>(Resource.Idle())
    val registerRequestResult: StateFlow<Resource<LoginResponse>> = _loginRequestResult
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
    private val _loginStatusResult = MutableStateFlow<Resource<Unit>>(Resource.Idle())
    fun updateLoginStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            _loginStatusResult.value = Resource.Loading()
            repository.setLoginStatus(isLoggedIn).collect { result ->
                _loginStatusResult.value = result
            }
        }
    }
    private val _loginStatusResultB = MutableStateFlow<Resource<Boolean?>>(Resource.Idle())
    val loginStatusResultB: StateFlow<Resource<Boolean?>> = _loginStatusResultB

    fun fetchLoginStatus() {
        viewModelScope.launch {
            _loginStatusResultB.value = Resource.Loading()
            repository.getLoginStatus().collect { result ->
                _loginStatusResultB.value = result
                _isLoading.value = false
                if (result is Resource.Success) {
                    _isLoading.value = false
                }
            }
        }
    }
}