package com.deletech.malakoff.models.login

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val message: String
)