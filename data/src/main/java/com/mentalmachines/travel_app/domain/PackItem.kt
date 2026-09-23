package com.mentalmachines.travel_app.domain

data class PackItem(
    val id: String,
    val name: String,
    val type: String,
    val category: String,
    val baseQuantityPerDay: Int,
    val isPacked: Boolean = false
)
