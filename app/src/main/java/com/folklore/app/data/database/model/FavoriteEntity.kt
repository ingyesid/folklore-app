package com.folklore.app.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = FavoriteEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
        ),
    ],
    indices = [
        Index("eventId"),
    ],
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val eventId: Long,
    val isFavorite: Boolean,
)
