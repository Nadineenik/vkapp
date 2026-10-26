package com.nadinee.vkapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.nadinee.vkapp.presentation.applistscreen.AppListScreen
import com.nadinee.vkapp.presentation.applistscreen.ShowcasePager
import com.nadinee.vkapp.presentation.model.UiAppInfo // ИСПРАВЛЕНО

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "app_list") {
        // Экран списка приложений
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    navController.navigate("app_details/${app.name}")
                }
            )
        }

        // Экран карточки приложения
        composable("app_details/{appName}") { backStackEntry ->
            val appName = backStackEntry.arguments?.getString("appName") ?: ""
            AppDetailsScreen(
                appName = appName,
                onBackClick = { navController.popBackStack() },
                onShowcaseAppClick = { clickedApp ->
                    navController.navigate("app_list") {
                        popUpTo("app_list") { inclusive = true }
                    }
                }
            )
        }

        // УДАЛЕН ДУБЛИРУЮЩИЙСЯ БЛОК composable("app_list")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    appName: String,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (UiAppInfo) -> Unit // ИСПРАВЛЕНО: используем UiAppInfo
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
