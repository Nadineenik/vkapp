package com.nadinee.vkapp.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.presentation.model.UiAppInfo

fun DomainAppInfo.toUiModel(): UiAppInfo {
    val imageVector = when (this.iconId) {
        "android" -> Icons.Default.Android
        "gamepad" -> Icons.Default.Gamepad
        "music" -> Icons.Default.MusicNote
        "apps" -> Icons.Default.Apps
        else -> Icons.Default.Apps
    }

    return UiAppInfo(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        icon = imageVector
    )
}
