package com.nadinee.vkapp.presentation.applistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadinee.vkapp.data.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onAppClick: (AppInfo) -> Unit,
    viewModel: AppListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveEvents(viewModel.events, snackbarHostState)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Мои приложения", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = "Иконка приложения",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .clickable { viewModel.clickLogo() } // Клик по логотипу в тулбаре
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        when (val currentState = state) {
            is AppListState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AppListState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentState.apps) { app ->
                        AppListItem(
                            app = app,
                            onClick = { onAppClick(app) },
                            onLogoTap = { viewModel.clickLogo() } // Клик по логотипу в карточке
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ObserveEvents(
    events: Flow<AppListEvent>,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(Unit) {
        events.collectLatest { event ->
            when (event) {
                AppListEvent.Error -> snackbarHostState.showSnackbar("Произошла ошибка")
                AppListEvent.LogoClicked -> snackbarHostState.showSnackbar("Вы нажали на логотип приложения")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListItem(
    app: AppInfo,
    onClick: () -> Unit,
    onLogoTap: () -> Unit
) {
    Card(
        onClick = onClick, // Клик по всей карточке
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = app.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onLogoTap() }, // Клик строго по иконке
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = app.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = app.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "Категория: ${app.category}", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium)
            }
        }
    }
}
