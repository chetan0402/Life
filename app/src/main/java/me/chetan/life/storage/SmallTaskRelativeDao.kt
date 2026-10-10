package me.chetan.life.storage

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface SmallTaskRelativeDao {
    @Insert
    suspend fun insert(task: SmallTaskRelative)
    @Delete
    suspend fun delete(task: SmallTaskRelative)
    @Query("SELECT * FROM SmallTaskRelative")
    suspend fun getAll(): List<SmallTaskRelative>
}