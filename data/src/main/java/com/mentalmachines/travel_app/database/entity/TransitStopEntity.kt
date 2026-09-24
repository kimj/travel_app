package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.StopState
import com.mentalmachines.travel_app.domain.TransitStop

@Entity(tableName = "transit_stops")
data class TransitStopEntity(
    @PrimaryKey
    val id: String,
    val tripId: String,
    val time: String,
    val locationName: String,
    val details: String,
    val state: String
)

fun TransitStopEntity.asDomainModel(): TransitStop {
    return TransitStop(
        id = id,
        time = time,
        locationName = locationName,
        details = details,
        state = try {
            StopState.valueOf(state)
        } catch (e: Exception) {
            StopState.UPCOMING
        }
    )
}

fun List<TransitStopEntity>.asDomainModel(): List<TransitStop> {
    return map { it.asDomainModel() }
}
