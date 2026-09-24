package com.mentalmachines.travel_app.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mentalmachines.travel_app.database.entity.TransitStopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransitStopDao {
    @Query("SELECT * FROM transit_stops WHERE tripId = :tripId")
    fun getTransitStopsForTrip(tripId: String): Flow<List<TransitStopEntity>>
}
