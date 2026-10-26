package com.nadinee.vkapp.domain.usecase

import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.domain.repository.AppRepository

class GetAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<DomainAppInfo> {
        return repository.getApps()
    }
}
