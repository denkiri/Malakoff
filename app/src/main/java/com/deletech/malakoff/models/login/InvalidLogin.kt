package com.deletech.malakoff.models.login

data class InvalidLogin(
    val code: Int,
    val `data`: Any,
    val message: String
)