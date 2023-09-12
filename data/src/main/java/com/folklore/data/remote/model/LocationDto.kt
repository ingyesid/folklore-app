package com.folklore.data.remote.model

import com.squareup.moshi.Json

data class LocationDto(
    @field:Json(name = "__type")
    val type: String,
    val latitude: Double,
    val longitude: Double,
)
