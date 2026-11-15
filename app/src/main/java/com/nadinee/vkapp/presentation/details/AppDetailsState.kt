package com.nadinee.vkapp.presentation.details

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(
        val appName: String,
        val installState: InstallState = InstallState.NotInstalled
    ) : AppDetailsState
}
