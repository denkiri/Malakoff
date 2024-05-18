package com.deletech.malakoff.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.models.projects.Projects
import com.deletech.malakoff.repository.HomeRepository
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
}