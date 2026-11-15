package com.nadinee.vkapp.presentation.applistscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadinee.vkapp.domain.repository.AppRepository
import com.nadinee.vkapp.domain.usecase.GetAppsUseCase
import com.nadinee.vkapp.presentation.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val repository: AppRepository
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
                val ids = domainApps.map { it.id }


                var parallelRatings = emptyMap<String, Float>()
                val parallelTime = measureTimeMillis {
                    parallelRatings = repository.loadRatingsInParallel(ids)
                }
                Log.d("TIMER", "Параллельно: $parallelTime мс")


                val uiApps = domainApps.map { domainApp ->
                    val rating = parallelRatings[domainApp.id] ?: 0f
                    domainApp.toUiModel().copy(rating = rating)
                }
                _state.update { AppListState.Content(uiApps) }


                launch(Dispatchers.IO) {
                    var sequentialRatings = emptyMap<String, Float>()
                    val sequentialTime = measureTimeMillis {
                        sequentialRatings = repository.loadRatingsSequentially(ids)
                    }
                    Log.d("TIMER", "Последовательно: $sequentialTime мс")
                }

            } catch (e: Exception) {

                Log.e("APP_ERROR", "Ошибка при загрузке: ${e.message}", e)
                _state.update { AppListState.Error }
            }
        }
    }

    fun clickLogo() {
        _events.trySend(AppListEvent.LogoClicked)
    }
}
