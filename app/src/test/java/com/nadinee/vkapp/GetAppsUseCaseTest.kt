package com.nadinee.vkapp

import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.domain.repository.AppRepository
import com.nadinee.vkapp.domain.usecase.GetAppsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAppsUseCaseTest {

    private val repository: AppRepository = mockk()
    private lateinit var getAppsUseCase: GetAppsUseCase

    @Before
    fun setUp() {
        getAppsUseCase = GetAppsUseCase(repository)
    }

    @Test
    fun `invoke returns list of apps when repository succeeds`() = runTest {
        val expectedApps = listOf(DomainAppInfo("1", "App1", "Desc", "Cat", "url"))
        coEvery { repository.getApps() } returns expectedApps

        val result = getAppsUseCase()

        assertEquals(expectedApps, result)
        coVerify(exactly = 1) { repository.getApps() }
    }

    @Test
    fun `invoke returns empty list when repository returns empty`() = runTest {
        coEvery { repository.getApps() } returns emptyList()

        val result = getAppsUseCase()

        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { repository.getApps() }
    }

    @Test
    fun `invoke throws exception when repository fails`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.getApps() } throws exception

        val result = runCatching { getAppsUseCase() }

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke calls repository exactly once`() = runTest {
        coEvery { repository.getApps() } returns listOf()

        getAppsUseCase()
        getAppsUseCase() // Вызываем дважды

        // Проверяем, что для каждого вызова useCase был ровно один вызов репозитория
        coVerify(exactly = 2) { repository.getApps() }
    }

    @Test
    fun `invoke preserves order of apps from repository`() = runTest {
        val apps = listOf(
            DomainAppInfo("1", "App1", "Desc", "Cat", "url"),
            DomainAppInfo("2", "App2", "Desc", "Cat", "url")
        )
        coEvery { repository.getApps() } returns apps

        val result = getAppsUseCase()

        assertEquals("1", result[0].id)
        assertEquals("2", result[1].id)
    }
}