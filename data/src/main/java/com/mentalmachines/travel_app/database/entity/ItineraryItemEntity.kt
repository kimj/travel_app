package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.ItineraryItem

@Entity(tableName = "itinerary_items")
data class ItineraryItemEntity(
    @PrimaryKey
    val id: String,
    val tripId: String,
    val dayNumber: Int,
    val dateText: String,
    val stopLocation: String,
    val timeRange: String
)

fun ItineraryItemEntity.asDomainModel(): ItineraryItem {
    return ItineraryItem(
        id = id,
        stopLocation = stopLocation,
        timeRange = timeRange
    )
}

fun List<ItineraryItemEntity>.asDomainModel(): List<ItineraryItem> {
    return map { it.asDomainModel() }
}
