package com.mentalmachines.travel_app.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mentalmachines.travel_app.database.entity.ItineraryItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itinerary_items WHERE tripId = :tripId ORDER BY dayNumber ASC")
    fun getItineraryForTrip(tripId: String): Flow<List<ItineraryItemEntity>>
}
