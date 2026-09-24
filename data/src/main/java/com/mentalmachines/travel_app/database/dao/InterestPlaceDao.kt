package com.mentalmachines.travel_app.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mentalmachines.travel_app.database.entity.InterestPlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestPlaceDao {
    @Query("SELECT * FROM interest_places WHERE tripId = :tripId")
    fun getPlacesForTrip(tripId: String): Flow<List<InterestPlaceEntity>>
}
