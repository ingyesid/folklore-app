package com.folklore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.folklore.data.database.model.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
)
abstract class FolkloreDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
}
