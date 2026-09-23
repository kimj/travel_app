package com.mentalmachines.travel_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.travel_app.domain.PackItem

@Entity(tableName = "pack_items")
data class PackItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val category: String,
    val baseQuantityPerDay: Int,
    val isPacked: Boolean = false
)

fun PackItemEntity.asDomainModel(): PackItem {
    return PackItem(
        id = id,
        name = name,
        type = type,
        category = category,
        baseQuantityPerDay = baseQuantityPerDay,
        isPacked = isPacked
    )
}

fun PackItem.asEntity(): PackItemEntity {
    return PackItemEntity(
        id = id,
        name = name,
        type = type,
        category = category,
        baseQuantityPerDay = baseQuantityPerDay,
        isPacked = isPacked
    )
}

fun List<PackItemEntity>.asDomainModel(): List<PackItem> {
    return map { it.asDomainModel() }
}
