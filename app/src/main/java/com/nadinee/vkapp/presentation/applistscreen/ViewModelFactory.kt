package com.nadinee.vkapp.presentation.applistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadinee.vkapp.data.repository.AppRepositoryImpl
import com.nadinee.vkapp.domain.usecase.GetAppsUseCase

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppListViewModel::class.java)) {
            // Ручное внедрение зависимостей (DI)
            val repository = AppRepositoryImpl()
            val useCase = GetAppsUseCase(repository)
            @Suppress("UNCHECKED_CAST")
            return AppListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
