package com.deletech.malakoff.models.login

data class ErrorLogin(
    val code: Int,
    val `data`: DataX,
    val message: String
)