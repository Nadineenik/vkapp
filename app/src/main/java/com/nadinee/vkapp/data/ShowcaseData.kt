package com.nadinee.vkapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.nadinee.vkapp.presentation.model.UiAppInfo

data class ShowcasePage(
    val apps: List<UiAppInfo>
)

val showcasePages = listOf(
    ShowcasePage(listOf(
        UiAppInfo("s1", "VK Звонки", "Бесплатные звонки", "Связь", Icons.Default.Star),
        UiAppInfo("s2", "VK Почта", "Быстрая почта", "Инструменты", Icons.Default.Mail),
        UiAppInfo("s3", "VK Облако", "Хранилище файлов", "Инструменты", Icons.Default.Cloud)
    )),
    ShowcasePage(listOf(
        UiAppInfo("s4", "VK Видео", "Смотрите видео", "Медиа", Icons.Default.PlayArrow),
        UiAppInfo("s5", "VK Маркет", "Покупки", "Покупки", Icons.Default.ShoppingCart),
        UiAppInfo("s6", "VK Знакомства", "Найдите друзей", "Социальные сети", Icons.Default.Person)
    )),
    ShowcasePage(listOf(
        UiAppInfo("s7", "VK Недвижимость", "Квартиры и дома", "Покупки", Icons.Default.Home),
        UiAppInfo("s8", "VK Работа", "Поиск вакансий", "Работа", Icons.Default.Work),
        UiAppInfo("s9", "VK Помощь", "Поддержка", "Инструменты", Icons.Default.Info)
    ))
)
