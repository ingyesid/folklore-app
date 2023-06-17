package com.folklore.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.folklore.app.data.database.model.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
)
abstract class FolkloreDatabase : RoomDatabase() {
    abstract val dao: EventDao
}
