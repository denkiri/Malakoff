package com.deletech.malakoff.screens.home.project.state
sealed class  ProjectUiEvent {
    data class ProjectNameChanged(val inputValue: String) : ProjectUiEvent()
    data class ProjectDescriptionChanged(val inputValue: String) : ProjectUiEvent()
    object Submit : ProjectUiEvent()
}