package com.folklore.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.folklore.app.data.database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(watchlist: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE eventId = :movieId LIMIT 1")
    fun getFavoritesList(movieId: Long): Flow<FavoriteEntity?>

    @Query("DELETE FROM favorites WHERE id = :id")
    fun delete(id: String)

}
