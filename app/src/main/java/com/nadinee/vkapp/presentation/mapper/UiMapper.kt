package com.nadinee.vkapp.presentation.mapper

import com.nadinee.vkapp.domain.model.DomainAppInfo
import com.nadinee.vkapp.presentation.model.UiAppInfo

fun DomainAppInfo.toUiModel(): UiAppInfo {
    return UiAppInfo(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        iconUrl = this.iconUrl
    )
}
