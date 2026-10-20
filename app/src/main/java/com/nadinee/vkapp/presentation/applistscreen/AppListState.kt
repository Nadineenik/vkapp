package com.nadinee.vkapp.presentation.applistscreen

import com.nadinee.vkapp.data.AppInfo

sealed interface AppListState {
    data object Loading : AppListState
    data class Content(val apps: List<AppInfo>) : AppListState
}
