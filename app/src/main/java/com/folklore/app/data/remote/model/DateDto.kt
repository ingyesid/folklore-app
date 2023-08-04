package com.folklore.app.data.remote.model

import com.squareup.moshi.Json

data class DateDto(
    @field:Json(name = "__type")
    val type: String,
    val iso: String,
)
