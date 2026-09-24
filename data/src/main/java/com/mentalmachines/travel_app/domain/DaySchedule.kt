package com.mentalmachines.travel_app.domain

data class ItineraryItem(
    val id: String,
    val stopLocation: String,
    val timeRange: String
)

data class DaySchedule(
    val dayNumber: Int,
    val dateText: String,
    val items: List<ItineraryItem>
)
