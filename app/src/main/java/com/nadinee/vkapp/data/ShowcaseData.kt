package com.nadinee.vkapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.nadinee.vkapp.presentation.model.UiAppInfo

data class ShowcasePage(
    val apps: List<UiAppInfo>
)

val showcasePages = listOf(
    ShowcasePage(listOf(
        UiAppInfo("s1", "VK Звонки", "Бесплатные звонки", "Связь", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s2", "VK Почта", "Быстрая почта", "Инструменты", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s3", "VK Облако", "Хранилище файлов", "Инструменты", "https://fastly.picsum.photos/id/10/200/200.jpg")
    )),
    ShowcasePage(listOf(
        UiAppInfo("s4", "VK Видео", "Смотрите видео", "Медиа", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s5", "VK Маркет", "Покупки", "Покупки", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s6", "VK Знакомства", "Найдите друзей", "Социальные сети", "https://fastly.picsum.photos/id/10/200/200.jpg")
    )),
    ShowcasePage(listOf(
        UiAppInfo("s7", "VK Недвижимость", "Квартиры и дома", "Покупки", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s8", "VK Работа", "Поиск вакансий", "Работа", "https://fastly.picsum.photos/id/10/200/200.jpg"),
        UiAppInfo("s9", "VK Помощь", "Поддержка", "Инструменты", "https://fastly.picsum.photos/id/10/200/200.jpg")
    ))
)
