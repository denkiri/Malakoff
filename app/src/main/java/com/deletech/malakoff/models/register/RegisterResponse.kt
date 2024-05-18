package com.deletech.malakoff.models.register

data class RegisterResponse(
    val code: Int,
    val `data`: Any,
    val message: String
)