package com.nadinee.vkapp.data.mapper

import com.nadinee.vkapp.data.dto.NetworkAppDto
import com.nadinee.vkapp.domain.model.DomainAppInfo

fun NetworkAppDto.toDomain(): DomainAppInfo {
    return DomainAppInfo(
        id = this.id,
        name = this.name,
        description = this.description ?: "Нет описания",
        category = this.category ?: "Без категории",
        iconUrl = this.iconUrl
    )
}