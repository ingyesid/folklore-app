package com.folklore.app.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
)
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val location: String,
    val startDate: String,
)
