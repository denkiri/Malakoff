package com.deletech.malakoff.endpoint
import com.deletech.malakoff.models.login.LoginDetails
import com.deletech.malakoff.models.login.LoginResponse
import com.deletech.malakoff.models.projects.CreateProject
import com.deletech.malakoff.models.projects.CreateProjectResponse
import com.deletech.malakoff.models.projects.Projects
import com.deletech.malakoff.models.register.RegisterDetails
import com.deletech.malakoff.models.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Singleton
@Singleton
interface MalakoffApi {
    @POST("login")
    fun login(@Body loginDetails: LoginDetails): Call<LoginResponse>
    @POST("register")
    fun register(@Body registerDetails: RegisterDetails): Call<RegisterResponse>
    @GET("projects")
    fun projects(@Header("Authorization") authToken:String?): Call<Projects>
    @POST("projects")
    fun createProject(@Header("Authorization") authToken:String?,@Body createProject : CreateProject): Call<CreateProjectResponse>
}



