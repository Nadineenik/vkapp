package com.nadinee.vkapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadinee.vkapp.data.AppInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen() {
    // Захардкоженные данные
    val apps = listOf(
        AppInfo(
            Icons.Default.Android,
            "VK App",
            "Официальное приложение ВКонтакте",
            "Социальные сети"
        ),
        AppInfo(Icons.Default.Gamepad, "Игровой центр", "Лучшие игры в одном месте", "Игры"),
        AppInfo(Icons.Default.MusicNote, "VK Музыка", "Слушайте музыку и подкасты", "Музыка"),
        AppInfo(Icons.Default.Apps, "Магазин", "Приложения и утилиты", "Инструменты")
    )

    // Scaffold задает базовую структуру экрана (тулбар, контент)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Мои приложения", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    // Иконка текущего приложения слева
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = "Иконка приложения",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    // Акцентный цвет задника (берем primary из темы)
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // LazyColumn для вертикального списка
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(apps) { app ->
                AppListItem(app)
            }
        }
    }
}

// Отдельный компонент для одного элемента списка
@Composable
fun AppListItem(app: AppInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка приложения
            Icon(
                imageVector = app.icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Текстовые данные
            Column {
                Text(
                    text = app.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = app.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Категория: ${app.category}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
