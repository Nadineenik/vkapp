package com.nadinee.vkapp.domain.repository

import com.nadinee.vkapp.domain.model.DomainAppInfo

interface AppRepository {
    suspend fun getApps(): List<DomainAppInfo>
}
