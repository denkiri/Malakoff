package com.deletech.malakoff.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.endpoint.MalakoffApi
import com.deletech.malakoff.models.login.LoginDetails
import com.deletech.malakoff.models.login.LoginResponse
import com.deletech.malakoff.models.register.RegisterDetails
import com.deletech.malakoff.models.register.RegisterResponse
import com.deletech.malakoff.storage.getLoginStatus
import com.deletech.malakoff.storage.setLoginStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
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
            val response = api.login(loginRequest).await()
            Resource.Loading(data = false)

            run {
                Log.d("Response", "loginData: $response")
                Resource.Success(data = response)
            }
        } catch (exception: Exception) {
            Log.d("ErrorResponse", "Error: ${exception.message.toString()}")
            Resource.Error(message = exception.message.toString())
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
            val response = api.register(registerRequest).await()
            Resource.Loading(data = false)

            run {
                Resource.Success(data = response)
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