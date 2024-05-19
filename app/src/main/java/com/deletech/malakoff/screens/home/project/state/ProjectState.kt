package com.deletech.malakoff.screens.home.project.state
import com.deletech.malakoff.components.ErrorState
data class ProjectState (
    val projectName: String = "",
    val projectDescription: String = "",
    val errorState: ProjectErrorState = ProjectErrorState(),
    val isProjectSuccessful: Boolean = false
)
data class ProjectErrorState(
    val projectNameErrorState: ErrorState = ErrorState(),
    val projectDescriptionErrorState: ErrorState = ErrorState()
)