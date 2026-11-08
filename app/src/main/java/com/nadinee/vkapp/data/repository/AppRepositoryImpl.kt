package com.nadinee.vkapp.data.repository

import com.nadinee.vkapp.data.mapper.toDomain
import com.nadinee.vkapp.data.network.CatalogApi
import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: CatalogApi // Внедряем API
) : AppRepository {

    override suspend fun getApps(): List<DomainAppInfo> {
        // Запрос к серверу
        val networkApps = api.getCatalog()
        // Конвертация
        return networkApps.map { it.toDomain() }
    }
}
