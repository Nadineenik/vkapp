package com.nadinee.vkapp.data.repository

import com.nadinee.vkapp.data.dto.AppDto
import com.nadinee.vkapp.data.mapper.toDomain
import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.domain.repository.AppRepository
import kotlinx.coroutines.delay

class AppRepositoryImpl : AppRepository {
    override suspend fun getApps(): List<DomainAppInfo> {
        delay(1000) // Имитация загрузки из сети

        // Тот самый хардкод, который мы перенесли из ViewModel
        val dtos = listOf(
            AppDto("1", "VK App", "Официальное приложение ВКонтакте", "Социальные сети", "android"),
            AppDto("2", "Игровой центр", "Лучшие игры в одном месте", "Игры", "gamepad"),
            AppDto("3", "VK Музыка", "Слушайте музыку и подкасты", "Музыка", "music"),
            AppDto("4", "Магазин", "Приложения и утилиты", "Инструменты", "apps")
        )

        // Конвертируем через маппер
        return dtos.map { it.toDomain() }
    }
}
