package com.nadinee.vkapp.presentation.applistscreen

import com.nadinee.vkapp.presentation.model.UiAppInfo

sealed interface AppListState {
    data object Loading : AppListState
    data class Content(val apps: List<UiAppInfo>) : AppListState
}
