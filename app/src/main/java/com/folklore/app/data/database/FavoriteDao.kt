package com.folklore.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.folklore.app.data.database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language

@Dao
interface FavoriteDao {

    @Language("RoomSql")
    @Query(
        """
            SELECT * FROM favorites
        """,
    )
    fun getAllFavorites(): Flow<List<FavoriteEntity>>


    @Language("RoomSql")
    @Query(
        """
            SELECT * FROM favorites WHERE id = :id
        """,
    )
    suspend fun getFavorite(id: String): FavoriteEntity?

    @Query(
        """
        DELETE  FROM favorites WHERE id = :eventId 
        """
    )
    suspend fun removeFavorite(eventId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEntity)

}
