package com.nadinee.vkapp.domain.repository

import com.nadinee.vkapp.domain.model.DomainAppInfo

interface AppRepository {
    suspend fun getApps(): List<DomainAppInfo>
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
