package com.nadinee.vkapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

data class ShowcasePage(
    val apps: List<AppInfo>
)

val showcasePages = listOf(
    ShowcasePage(listOf(
        AppInfo(Icons.Default.Star, "VK Звонки", "Бесплатные звонки", "Связь"),
        AppInfo(Icons.Default.Mail, "VK Почта", "Быстрая почта", "Инструменты"),
        AppInfo(Icons.Default.Cloud, "VK Облако", "Хранилище файлов", "Инструменты")
    )),
    ShowcasePage(listOf(
        AppInfo(Icons.Default.PlayArrow, "VK Видео", "Смотрите видео", "Медиа"),
        AppInfo(Icons.Default.ShoppingCart, "VK Маркет", "Покупки", "Покупки"),
        AppInfo(Icons.Default.Person, "VK Знакомства", "Найдите друзей", "Социальные сети")
    )),
    ShowcasePage(listOf(
        AppInfo(Icons.Default.Home, "VK Недвижимость", "Квартиры и дома", "Покупки"),
        AppInfo(Icons.Default.Work, "VK Работа", "Поиск вакансий", "Работа"),
        AppInfo(Icons.Default.Info, "VK Помощь", "Поддержка", "Инструменты")
    ))
)
