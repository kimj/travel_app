package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.Trip

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TripEntity(
    @PrimaryKey
    val id: String,
    val userId: Int,
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
        userId = 1, // Defaulting to the seed user (alex_traveler) for newly drafted trips
        destination = destination,
        duration = duration
    )
}

fun List<TripEntity>.asDomainModel(): List<Trip> {
    return map { it.asDomainModel() }
}
