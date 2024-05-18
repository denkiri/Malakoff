package com.deletech.malakoff.models.register

data class ErrorRegistration(
    val code: Int,
    val `data`: Data,
    val message: String
)