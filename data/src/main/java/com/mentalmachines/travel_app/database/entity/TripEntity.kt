package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.Trip

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey
    val id: String,
    val destination: String,
    val duration: String
)

fun TripEntity.asDomainModel(): Trip {
    return Trip(
        id = id,
        destination = destination,
        duration = duration
    )
}

fun Trip.asEntity(): TripEntity {
    return TripEntity(
        id = id,
        destination = destination,
        duration = duration
    )
}

fun List<TripEntity>.asDomainModel(): List<Trip> {
    return map { it.asDomainModel() }
}
