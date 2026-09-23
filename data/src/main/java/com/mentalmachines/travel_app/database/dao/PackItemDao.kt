package com.mentalmachines.travel_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalmachines.travel_app.database.entity.PackItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PackItemDao {
    @Query("SELECT * FROM pack_items")
    fun getAllPackItems(): Flow<List<PackItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PackItemEntity>)

    @Query("UPDATE pack_items SET isPacked = :isPacked WHERE id = :id")
    suspend fun updatePackedStatus(id: String, isPacked: Boolean)

    @Query("SELECT COUNT(*) FROM pack_items")
    suspend fun getCount(): Int
}
