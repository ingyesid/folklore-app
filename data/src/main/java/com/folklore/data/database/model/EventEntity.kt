package com.folklore.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val shortDescription: String,
    val description: String,
    val imageUrl: String,
    val website: String,
    val goingCount: Int,
    val likes: Int,
    val startAt: Long,
    val endsAt: Long,
    val city: String,
    val state: String,
    val status: String,
    val latitude: Double,
    val longitude: Double,
)
