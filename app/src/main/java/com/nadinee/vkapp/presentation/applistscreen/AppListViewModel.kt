package com.nadinee.vkapp.presentation.applistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadinee.vkapp.domain.usecase.GetAppsUseCase
import com.nadinee.vkapp.presentation.mapper.toUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppListViewModel(
    private val getAppsUseCase: GetAppsUseCase // Внедряем UseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppListEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            try {
                val domainApps = getAppsUseCase()
                val uiApps = domainApps.map { it.toUiModel() } // Мапим в UI модель
                _state.update { AppListState.Content(uiApps) }
            } catch (e: Exception) {
                _state.update { AppListState.Content(emptyList()) }
                _events.trySend(AppListEvent.Error)
            }
        }
    }

    fun clickLogo() {
        _events.trySend(AppListEvent.LogoClicked)
    }
}
