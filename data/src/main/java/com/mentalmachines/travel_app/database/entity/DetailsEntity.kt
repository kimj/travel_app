package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.Details

@Entity(tableName = "details")
data class DetailsEntity(
    @PrimaryKey
    val user: String,
    val avatar: String,
    val name: String,
    val userSince: String,
    val location: String
)

fun DetailsEntity.asDomainModel(): Details {
    return Details(
        user = user,
        avatar = avatar,
        name = name,
        userSince = userSince,
        location = location
    )
}
