package com.nadinee.vkapp.presentation.applistscreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MusicNote
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadinee.vkapp.data.AppInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AppListViewModel : ViewModel() {

    // Состояние экрана
    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state = _state.asStateFlow()

    // Одноразовые события (Snackbar)
    private val _events = Channel<AppListEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            delay(1.seconds) // Имитация загрузки (чтобы увидеть CircularProgressIndicator)

            // Ваши приложения из предыдущего ДЗ
            val mockApps = listOf(
                AppInfo(Icons.Default.Android, "VK App", "Официальное приложение ВКонтакте", "Социальные сети"),
                AppInfo(Icons.Default.Gamepad, "Игровой центр", "Лучшие игры в одном месте", "Игры"),
                AppInfo(Icons.Default.MusicNote, "VK Музыка", "Слушайте музыку и подкасты", "Музыка"),
                AppInfo(Icons.Default.Apps, "Магазин", "Приложения и утилиты", "Инструменты")
            )
            _state.update { AppListState.Content(mockApps) }
        }
    }

    // Метод для клика по логотипу
    fun clickLogo() {
        _events.trySend(AppListEvent.LogoClicked)
    }
}