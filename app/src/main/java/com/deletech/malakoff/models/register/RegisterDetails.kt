package com.deletech.malakoff.models.register

data class RegisterDetails(
    val username:String,
    val email:String,
    val phone:String,
    val password: String,
    val confirm_password:String
)
