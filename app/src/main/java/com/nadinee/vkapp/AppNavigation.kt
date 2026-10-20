package com.nadinee.vkapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nadinee.vkapp.data.AppInfo
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "app_list") {
        // Экран списка приложений (из предыдущего ДЗ)
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    // Переход на карточку с передачей ID
                    navController.navigate("app_details/${app.name}")
                }
            )
        }

        // Экран карточки приложения (из репозитория VkEducation + Витрина)
        composable("app_details/{appName}") { backStackEntry ->
            val appName = backStackEntry.arguments?.getString("appName") ?: ""
            AppDetailsScreen(
                appName = appName,
                onBackClick = { navController.popBackStack() },
                onShowcaseAppClick = { clickedApp ->
                    // Переход обратно на список при клике на Витрину
                    navController.navigate("app_list") {
                        popUpTo("app_list") { inclusive = true }
                    }
                }
            )
        }

        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    navController.navigate("app_details/${app.name}")
                }
            )
        }
    }
}

// Упрощенный экран карточки приложения (взят за основу из репозитория)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    appName: String,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (AppInfo) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Описание приложения $appName",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            ShowcasePager(
                onAppClick = onShowcaseAppClick
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
