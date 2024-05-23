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
import com.deletech.malakoff.models.projects.CreateProject
import com.deletech.malakoff.models.projects.CreateProjectResponse
import com.deletech.malakoff.models.projects.FormError
import com.deletech.malakoff.models.projects.Projects
import com.deletech.malakoff.models.projects.archiveProject.ArchiveProjectResponse
import com.deletech.malakoff.models.projects.archiveProject.ErrorResponse
import com.google.gson.Gson
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: MalakoffApi, application: Application){
    private val context: Context
    init {
        context=application.applicationContext

    }
    suspend fun projects(authToken:String?): Resource<Projects> {
        return try {
            Resource.Loading(data = true)
            val response = api.projects(authToken).await()
            Resource.Loading(data = false)

            run {
                Resource.Success(data = response)
            }
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }
    suspend fun createProject(authToken:String?,projectName: String, projectDescription: String): Resource<CreateProjectResponse> {
        val createProject = CreateProject(projectName, projectDescription)
        return try {
            Resource.Loading(data = true)
            val response = api.createProject(authToken, createProject).awaitResponse()
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
                        val errorResponse = Gson().fromJson(it, FormError::class.java)
                        errorResponse.message
                    } ?: "Bad request"
                    Resource.Error(message = errorMessage)
                }
                401 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, FormError::class.java)
                        errorResponse.message
                    } ?: "Unauthorized"
                    Resource.Error(message = errorMessage)
                }
                500 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, FormError::class.java)
                        errorResponse.message
                    } ?: "Server error"
                    Resource.Error(message = errorMessage)
                }
                else -> {
                    Resource.Error(message = "Unexpected response code ${response.code()}")
                }
            }
        } catch (exception: Exception) {
            Resource.Error(message = exception.message ?: "An unknown error occurred")
        }
    }
    suspend fun deleteProject(authToken:String?,projectId: String): Resource<ArchiveProjectResponse> {
        return try {
            Resource.Loading(data = true)
            val response = api.deleteProject(authToken,projectId).awaitResponse()
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
                404 -> {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                        errorResponse.message
                    } ?: "Not Found"
                    Resource.Error(message = errorMessage)
                }
                else -> {
                    Resource.Error(message = "Unexpected response code ${response.code()}")
                }
            }
        } catch (exception: Exception) {
            Resource.Error(message = exception.message ?: "An unknown error occurred")
        }
    }

}