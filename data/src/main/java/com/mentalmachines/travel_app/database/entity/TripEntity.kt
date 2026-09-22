package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity constructor(
    @PrimaryKey
    val id : Int
)