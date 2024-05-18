package com.deletech.malakoff.models.register

data class Data(
    val confirm_password: List<String>,
    val email: List<String>,
    val password: List<String>,
    val phone: List<String>,
    val username: List<String>
)