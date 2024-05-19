package com.deletech.malakoff.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.endpoint.MalakoffApi
import com.deletech.malakoff.models.login.ErrorLogin
import com.deletech.malakoff.models.login.InvalidLogin
import com.deletech.malakoff.models.login.LoginDetails
import com.deletech.malakoff.models.login.LoginResponse
import com.deletech.malakoff.models.register.ErrorRegistration
import com.deletech.malakoff.models.register.RegisterDetails
import com.deletech.malakoff.models.register.RegisterResponse
import com.deletech.malakoff.storage.getLoginStatus
import com.deletech.malakoff.storage.setLoginStatus
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: MalakoffApi,  application: Application){
    private val context: Context
    init {
        context=application.applicationContext

    }
    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        val loginRequest = LoginDetails(username, password)
        return try {
            Resource.Loading(data = true)
            val response = api.login(loginRequest).awaitResponse()
            Resource.Loading(data = false)
            when (response.code()) {
                200 -> {
                    val body = response.body()
                    if (body != null && body.code == 0) {
                        Resource.Success(data = body)
                    } else {
                        Resource.Error(message = body?.message ?: "Unknown error")
                    }
                }
                400 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, ErrorLogin::class.java)
                        errorResponse.message
                    } ?: "Bad request"
                    Resource.Error(message = errorMessage)
                }
                401 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, InvalidLogin::class.java)
                        errorResponse.message
                    } ?: "Unauthorized"
                    Resource.Error(message = errorMessage)
                }
                500 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, InvalidLogin::class.java)
                        errorResponse.message
                    } ?: "Server error"
                    Resource.Error(message = errorMessage)
                }
                else -> {
                    Log.d("ErrorResponse", "Error: Unexpected response code ${response.code()}")
                    Resource.Error(message = "Unexpected response code ${response.code()}")
                }
            }
        } catch (exception: Exception) {
            Log.d("ErrorResponse", "Exception: ${exception.message}")
            Resource.Error(message = exception.message ?: "An unknown error occurred")
        }
    }
    suspend fun register(username:String,
                             email:String,
                            phone:String,
                             password: String,
                             confirm_password:String): Resource<RegisterResponse> {
        val registerRequest = RegisterDetails(username, email, phone, password, confirm_password)
        return try {
            Resource.Loading(data = true)
            val response = api.register(registerRequest).awaitResponse()
            Resource.Loading(data = false)

//            run {
//                Resource.Success(data = response)
//            }

            when (response.code()) {
                200 -> {
                    val body = response.body()
                    if (body != null && body.code == 0) {
                        Resource.Success(data = body)
                    } else {
                        Resource.Error(message = body?.message ?: "Unknown error")
                    }
                }
                400 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, ErrorRegistration::class.java)
                        errorResponse.message
                    } ?: "Bad request"
                    Resource.Error(message = errorMessage)
                }
                401 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, ErrorRegistration::class.java)
                        errorResponse.message
                    } ?: "Unauthorized"
                    Resource.Error(message = errorMessage)
                }
                500 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, ErrorRegistration::class.java)
                        errorResponse.message
                    } ?: "Server error"
                    Resource.Error(message = errorMessage)
                }
                else -> {
                    Log.d("ErrorResponse", "Error: Unexpected response code ${response.code()}")
                    Resource.Error(message = "Unexpected response code ${response.code()}")
                }
            }
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }
    suspend fun setLoginStatus(isLoggedIn: Boolean): Flow<Resource<Unit>> = flow {
        try {
            context.setLoginStatus(isLoggedIn)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message, data = null))
        }
    }
    suspend fun getLoginStatus(): Flow<Resource<Boolean?>> = flow {
        try {
            val loginStatus = context.getLoginStatus()
            emit(Resource.Success(loginStatus))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message, data = null))
        }
    }
}