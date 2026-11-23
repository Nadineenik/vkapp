package com.nadinee.vkapp

import com.nadinee.vkapp.data.dto.NetworkAppDto
import com.nadinee.vkapp.data.mapper.toDomain
import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.presentation.mapper.toUiModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppMapperTest {

    @Test
    fun `toDomain converts valid DTO with all fields`() {
        // Используем реальный NetworkAppDto
        val dto = NetworkAppDto(
            id = "1", // ⚠️ Если в твоем DTO поле id имеет тип Int, поменяй "1" на 1
            name = "VK App",
            description = "Social network",
            category = "Social",
            iconUrl = "https://example.com/icon.png"
        )

        val domain = dto.toDomain()

        assertEquals("1", domain.id)
        assertEquals("VK App", domain.name)
        assertEquals("Social network", domain.description)
        assertEquals("Social", domain.category)
        assertEquals("https://example.com/icon.png", domain.iconUrl)
    }

    @Test
    fun `toDomain handles null description and category with defaults`() {
        val dto = NetworkAppDto(
            id = "2",
            name = "Test App",
            description = null,
            category = null,
            iconUrl = "url"
        )

        val domain = dto.toDomain()

        // Проверяем, что оператор ?: подставил значения по умолчанию
        assertEquals("Нет описания", domain.description)
        assertEquals("Без категории", domain.category)
    }

    @Test
    fun `toDomain handles empty strings correctly`() {
        val dto = NetworkAppDto(
            id = "3",
            name = "Empty App",
            description = "",
            category = "",
            iconUrl = ""
        )

        val domain = dto.toDomain()

        // Оператор ?: срабатывает только на null, поэтому пустые строки остаются пустыми
        assertEquals("", domain.description)
        assertEquals("", domain.category)
    }

    @Test
    fun `toUiModel converts Domain to UiModel correctly`() {
        val domain = DomainAppInfo(
            id = "1",
            name = "VK App",
            description = "Social",
            category = "Social",
            iconUrl = "url"
        )

        val uiModel = domain.toUiModel()

        assertEquals("1", uiModel.id)
        assertEquals("VK App", uiModel.name)
        assertEquals("Social", uiModel.description)
        assertEquals("Social", uiModel.category)
        assertEquals("url", uiModel.iconUrl)
    }

    @Test
    fun `toUiModel sets rating to null by default`() {
        val domain = DomainAppInfo(
            id = "1",
            name = "VK App",
            description = "Social",
            category = "Social",
            iconUrl = "url"
        )

        val uiModel = domain.toUiModel()

        // Проверяем, что рейтинг изначально null, как и задумано в маппере
        assertNull(uiModel.rating)
    }
}
