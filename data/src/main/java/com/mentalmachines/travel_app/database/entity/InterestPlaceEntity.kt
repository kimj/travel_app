package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.InterestPlace

@Entity(tableName = "interest_places")
data class InterestPlaceEntity(
    @PrimaryKey
    val id: String,
    val tripId: String,
    val name: String,
    val foodType: String,
    val rating: Double,
    val pricePoint: String,
    val tags: String, // Comma-separated string for simplicity
    val offsetX: Int,
    val offsetY: Int
)

fun InterestPlaceEntity.asDomainModel(): InterestPlace {
    return InterestPlace(
        id = id,
        name = name,
        foodType = foodType,
        rating = rating,
        pricePoint = pricePoint,
        tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        offsetX = offsetX,
        offsetY = offsetY
    )
}

fun List<InterestPlaceEntity>.asDomainModel(): List<InterestPlace> {
    return map { it.asDomainModel() }
}
