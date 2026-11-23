package com.nadinee.vkapp

import com.nadinee.vkapp.data.dto.NetworkAppDto
import com.nadinee.vkapp.data.network.CatalogApi
import com.nadinee.vkapp.data.repository.AppRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {

    // 1. Мокаем именно реальный интерфейс CatalogApi, а не придуманный MockAppApi
    private val api: CatalogApi = mockk()

    // 2. Создаем экземпляр реального репозитория, передавая в него мок API
    private lateinit var repository: AppRepositoryImpl

    @Before
    fun setUp() {
        repository = AppRepositoryImpl(api)
    }

    @Test
    fun `getApps returns domain models when API succeeds`() = runTest {
        // Arrange (Подготовка)
        val dtoList = listOf(
            NetworkAppDto(id = "1", name = "VK App", description = "Social", category = "Social", iconUrl = "url1"),
            NetworkAppDto(id = "2", name = "Telegram", description = null, category = null, iconUrl = "url2")
        )
        // Говорим моку: когда вызовут getCatalog(), верни этот список
        coEvery { api.getCatalog() } returns dtoList

        // Act (Действие)
        val result = repository.getApps()

        // Assert (Проверка)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("VK App", result[0].name)

        // Проверяем, что маппер сработал и подставил значения по умолчанию для null-полей
        assertEquals("Нет описания", result[1].description)
        assertEquals("Без категории", result[1].category)

        // Проверяем, что метод API был вызван ровно 1 раз
        coVerify(exactly = 1) { api.getCatalog() }
    }

    @Test
    fun `getApps returns empty list when API returns empty`() = runTest {
        // Arrange
        coEvery { api.getCatalog() } returns emptyList()

        // Act
        val result = repository.getApps()

        // Assert
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { api.getCatalog() }
    }

    @Test
    fun `getApps throws exception on network failure`() = runTest {
        // Arrange
        val exception = RuntimeException("No internet")
        coEvery { api.getCatalog() } throws exception

        // Act & Assert
        val result = runCatching { repository.getApps() }
        assertTrue(result.isFailure)
        assertEquals("No internet", result.exceptionOrNull()?.message)
    }

    @Test
    fun `loadRatingsSequentially returns map with correct keys and valid range`() = runTest {
        // Arrange
        val ids = listOf("app_1", "app_2")

        // Act
        val result = repository.loadRatingsSequentially(ids)

        // Assert
        assertEquals(2, result.size)
        assertTrue(result.containsKey("app_1"))
        assertTrue(result.containsKey("app_2"))

        // Так как emulateGetRating генерирует случайное число от 3.0 до 5.0,
        // мы не можем проверить точное значение, но можем проверить, что оно попадает в этот диапазон
        assertTrue(result["app_1"]!! in 3.0f..5.0f)
        assertTrue(result["app_2"]!! in 3.0f..5.0f)
    }

    @Test
    fun `loadRatingsInParallel returns map with correct keys and valid range`() = runTest {
        // Arrange
        val ids = listOf("app_1", "app_2", "app_3")

        // Act
        val result = repository.loadRatingsInParallel(ids)

        // Assert
        assertEquals(3, result.size)
        assertTrue(result.containsKey("app_1"))
        assertTrue(result.containsKey("app_2"))
        assertTrue(result.containsKey("app_3"))

        // Проверяем, что все сгенерированные рейтинги в корректном диапазоне
        result.values.forEach { rating ->
            assertTrue(rating in 3.0f..5.0f)
        }
    }
}
