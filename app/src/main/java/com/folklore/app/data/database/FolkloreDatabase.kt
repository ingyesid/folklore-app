package com.folklore.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.folklore.app.data.database.model.EventEntity
import com.folklore.app.data.database.model.FavoriteEntity

@Database(
    entities = [EventEntity::class, FavoriteEntity::class],
    version = 1,
)
abstract class FolkloreDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
    abstract val favoriteDao: FavoriteDao
}
