package com.nadinee.vkapp.data.mapper

import com.nadinee.vkapp.data.dto.AppDto
import com.nadinee.vkapp.domain.model.DomainAppInfo

fun AppDto.toDomain(): DomainAppInfo {
    return DomainAppInfo(
        id = this.id,
        name = this.title,
        description = this.description,
        category = this.category,
        iconId = this.iconName
    )
}
