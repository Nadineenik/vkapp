package com.nadinee.vkapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private var installJob: Job? = null


    fun loadDetails(appName: String) {
        _state.update { AppDetailsState.Content(appName = appName) }
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {

            installJob?.cancel()
            _state.update {
                (it as AppDetailsState.Content).copy(installState = InstallState.NotInstalled)
            }
        } else if (current.installState is InstallState.NotInstalled) {

            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        try {
            _state.update {
                (it as AppDetailsState.Content).copy(installState = InstallState.Installing(0))
            }

            for (progress in 5..100 step 5) {
                delay(150) // Задержка
                _state.update {
                    (it as AppDetailsState.Content).copy(installState = InstallState.Installing(progress))
                }
            }


            _state.update {
                (it as AppDetailsState.Content).copy(installState = InstallState.Installed)
            }
        } catch (e: Exception) {
            // Если корутину отменили, сюда прилетит CancellationException,
            // но состояние мы уже сбросили в onInstallClick
        }
    }
}