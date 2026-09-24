package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity

@Entity
data class Shopping(
    val mall_id : Int,
    val mall_name : String,
    val city_id : Int,
    val address : String,
    val description : String
)

@Entity
data class EmergencyService(
    val service_id : Int,
    val city_id : Int,
    val service_type : String,
    val contact_number : String
)

@Entity
data class Monument(
    val monument_id : Int,
    val city_id : Int,
    val monument_name : String,
    val description : String
)

@Entity
data class TravelTip(
    val tip_id : Int,
    val city_id : Int,
    val tip_text : String,
)
