package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity

@Entity
data class Shopping constructor(
    val mall_id : Int,
    val mall_name : String,
    val city_id : Int,
    val address : String,
    val description : String
)

@Entity
data class EmergencyService constructor(
    val service_id : Int,
    val city_id : Int,
    val service_type : String,
    val contact_number : String
)

@Entity
data class Monument constructor(
    val monument_id : Int,
    val city_id : Int,
    val monument_name : String,
    val description : String
)

@Entity
data class TravelTip constructor(
    val tip_id : Int,
    val city_id : Int,
    val tip_text : String,
)
