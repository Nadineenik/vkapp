package com.nadinee.vkapp.data.network

import com.nadinee.vkapp.data.dto.NetworkAppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<NetworkAppDto>

    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): NetworkAppDto
}