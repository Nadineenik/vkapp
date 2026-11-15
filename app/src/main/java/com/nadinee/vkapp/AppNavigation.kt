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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nadinee.vkapp.presentation.applistscreen.AppListScreen
import com.nadinee.vkapp.presentation.applistscreen.ShowcasePager
import com.nadinee.vkapp.presentation.details.AppDetailsState
import com.nadinee.vkapp.presentation.details.AppDetailsViewModel
import com.nadinee.vkapp.presentation.details.InstallState
import com.nadinee.vkapp.presentation.model.UiAppInfo

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "app_list") {

        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    navController.navigate("app_details/${app.name}")
                }
            )
        }


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


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    appName: String,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (UiAppInfo) -> Unit,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(appName) {
        viewModel.loadDetails(appName)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

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
        when (val currentState = state) {
            is AppDetailsState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AppDetailsState.Content -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Описание приложения ${currentState.appName}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    InstallButton(
                        installState = currentState.installState,
                        onInstallClick = { viewModel.onInstallClick() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ShowcasePager(onAppClick = onShowcaseAppClick)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun InstallButton(
    installState: InstallState,
    onInstallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onInstallClick,
        modifier = modifier.fillMaxWidth().height(50.dp)
    ) {
        when (installState) {
            is InstallState.NotInstalled -> Text("Установить")
            is InstallState.Installing -> Text("Отменить (${installState.progress}%)")
            is InstallState.Installed -> Text("Установлено")
        }
    }
}
