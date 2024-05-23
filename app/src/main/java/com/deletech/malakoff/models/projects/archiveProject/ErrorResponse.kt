package com.deletech.malakoff.models.projects.archiveProject

data class ErrorResponse(
    val code: Int,
    val `data`: Any,
    val message: String
)