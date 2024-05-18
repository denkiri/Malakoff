package com.deletech.malakoff.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.endpoint.MalakoffApi
import com.deletech.malakoff.models.projects.Projects
import retrofit2.await
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
}