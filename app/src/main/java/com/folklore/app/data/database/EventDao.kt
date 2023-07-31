package com.folklore.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.folklore.app.data.database.model.EventEntity
import org.intellij.lang.annotations.Language

@Dao
interface EventDao {

    @Language("RoomSql")
    @Query(
        """
            SELECT * FROM events WHERE LOWER(title) LIKE '%' || LOWER(:query) AND status = 'active'
        """,
    )
    suspend fun getAllEvents(query: String): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getEvent(id: String): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvents(events: List<EventEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEvent(event: EventEntity)
}
