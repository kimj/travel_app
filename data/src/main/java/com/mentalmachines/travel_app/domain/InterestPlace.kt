package com.mentalmachines.travel_app.domain

data class InterestPlace(
    val id: String,
    val name: String,
    val foodType: String,
    val rating: Double,
    val pricePoint: String,
    val tags: List<String>,
    val offsetX: Int,
    val offsetY: Int
)
