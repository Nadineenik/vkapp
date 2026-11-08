package com.nadinee.vkapp.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector

data class UiAppInfo(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val icon: ImageVector
)
