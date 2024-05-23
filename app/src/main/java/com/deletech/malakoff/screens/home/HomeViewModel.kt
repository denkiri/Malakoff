package com.deletech.malakoff.screens.home
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deletech.malakoff.components.ErrorState
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.models.projects.CreateProjectResponse
import com.deletech.malakoff.models.projects.Projects
import com.deletech.malakoff.models.projects.archiveProject.ArchiveProjectResponse
import com.deletech.malakoff.repository.HomeRepository
import com.deletech.malakoff.screens.home.project.state.ProjectErrorState
import com.deletech.malakoff.screens.home.project.state.ProjectState
import com.deletech.malakoff.screens.home.project.state.ProjectUiEvent
import com.deletech.malakoff.screens.login.state.emailEmptyErrorState
import com.deletech.malakoff.screens.login.state.passwordEmptyErrorState
import com.deletech.malakoff.screens.login.state.projectDescriptionEmptyErrorState
import com.deletech.malakoff.screens.login.state.projectNameEmptyErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    private val _projectDataRequestResult = MutableStateFlow<Resource<Projects>>(Resource.Idle())
    val projectReportRequestResult: StateFlow<Resource<Projects>> = _projectDataRequestResult
    fun getProjects(token:String) {
        viewModelScope.launch {
            _projectDataRequestResult.value = Resource.Loading()
            try {
                val reportResponse = repository.projects(token)
                if (reportResponse is Resource.Success) {
                    _isLoading.value = false
                    _projectDataRequestResult.value = Resource.Success(reportResponse.data!!)
                } else {
                    _projectDataRequestResult.value = Resource.Error(reportResponse.message)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _projectDataRequestResult.value = Resource.Error("loading failed: ${e.message}")
            }
        }
    }
    var projectState = mutableStateOf(ProjectState())
    fun resetStates() {
        projectState.value = ProjectState()
    }
    val isLoading: StateFlow<Boolean> = _isLoading
    private fun validateInputs(): Boolean {
        val projectNameString = projectState.value.projectName.trim()
        val projectDescriptionString = projectState.value.projectDescription
        return when {

            projectNameString.isEmpty() -> {
                projectState.value = projectState.value.copy(
                    errorState = ProjectErrorState(
                        projectNameErrorState = projectNameEmptyErrorState
                    )
                )
                false
            }

            projectDescriptionString.isEmpty() -> {
                projectState.value = projectState.value.copy(
                    errorState = ProjectErrorState(
                        projectDescriptionErrorState = projectDescriptionEmptyErrorState
                    )
                )
                false
            }

            else -> {
                projectState.value = projectState.value.copy(projectName = projectNameString)

                projectState.value = projectState.value.copy(errorState = ProjectErrorState())
                true
            }
        }
    }
    fun onUiEvent(projectUiEvent: ProjectUiEvent) {
        when (projectUiEvent) {
            is ProjectUiEvent.ProjectNameChanged -> {
                projectState.value = projectState.value.copy(
                    projectName = projectUiEvent.inputValue,
                    errorState =  projectState.value.errorState.copy(
                        projectNameErrorState = if (projectUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }
            is ProjectUiEvent.ProjectDescriptionChanged -> {
                projectState.value =  projectState.value.copy(
                    projectDescription = projectUiEvent.inputValue,
                    errorState =  projectState.value.errorState.copy(
                        projectDescriptionErrorState = if (projectUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }
            is ProjectUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    isLoading
                    projectState.value =  projectState.value.copy(isProjectSuccessful = true)


                }
            }
        }
    }
    private val _createProjectRequestResult = MutableStateFlow<Resource<CreateProjectResponse>>(Resource.Idle())
    val createProjectRequestResult: StateFlow<Resource<CreateProjectResponse>> = _createProjectRequestResult
    fun createProject(token:String,projectName: String, projectDescription: String) {
        viewModelScope.launch {
            _createProjectRequestResult.value = Resource.Loading()
            try {
                val createProjectResponse = repository.createProject(token,projectName, projectDescription)
                if (createProjectResponse is Resource.Success) {
                    _isLoading.value = false
                    _createProjectRequestResult.value = Resource.Success(createProjectResponse.data!!)
                } else {
                    _createProjectRequestResult.value = Resource.Error(createProjectResponse.message)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _createProjectRequestResult.value = Resource.Error(" failed: ${e.message}")
            }
        }
    }
    private val _archiveProjectRequestResult = MutableStateFlow<Resource<ArchiveProjectResponse>>(Resource.Idle())
    val deleteProjectRequestResult: StateFlow<Resource<ArchiveProjectResponse>> = _archiveProjectRequestResult
    fun deleteProject(token:String,projectId: String) {
        viewModelScope.launch {
            _archiveProjectRequestResult.value = Resource.Loading()
            try {
                val archiveProjectResponse = repository.deleteProject(token,projectId)
                if (archiveProjectResponse is Resource.Success) {
                    getProjects(token)
                    _isLoading.value = false
                    _archiveProjectRequestResult.value = Resource.Success(archiveProjectResponse.data!!)
                } else {
                    _archiveProjectRequestResult.value = Resource.Error(archiveProjectResponse.message)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _archiveProjectRequestResult.value = Resource.Error(" failed: ${e.message}")
            }
        }
    }


}