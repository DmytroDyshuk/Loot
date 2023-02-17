package com.example.loot.data

import androidx.room.*
import com.example.loot.model.Loot
import kotlinx.coroutines.flow.Flow

@Dao
interface LootDao {
    @Query("SELECT * FROM loot_database")
    fun getAllLoot(): Flow<List<Loot>>

    @Query("SELECT * FROM loot_database WHERE id = :id")
    fun getLoot(id: Long): Flow<Loot>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLoot(loot: Loot)

    @Update
    suspend fun updateLoot(loot: Loot)

    @Delete
    suspend fun deleteLoot(loot: Loot)
}