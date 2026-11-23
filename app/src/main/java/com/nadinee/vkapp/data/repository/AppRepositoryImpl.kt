package com.nadinee.vkapp.data.repository

import com.nadinee.vkapp.data.mapper.toDomain
import com.nadinee.vkapp.data.network.CatalogApi
import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.domain.repository.AppRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
class AppRepositoryImpl @Inject constructor(
    private val api: CatalogApi
) : AppRepository {

    override suspend fun getApps(): List<DomainAppInfo> {
        return api.getCatalog().map { it.toDomain() }
    }

    // Имитация запроса рейтинга
    private suspend fun emulateGetRating(id: String): Float {
        delay(1500) // Эмуляция сетевой задержки
        return (30..50).random() / 10f // Случайный рейтинг от 3.0 до 5.0
    }

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            ratings[id] = emulateGetRating(id) // Используем нашу эмуляцию
        }
        return ratings
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async {
                id to emulateGetRating(id) // Используем нашу эмуляцию
            }
        }.awaitAll().toMap()
    }
}
