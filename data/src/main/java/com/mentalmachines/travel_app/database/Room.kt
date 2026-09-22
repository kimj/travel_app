package com.mentalmachines.travel_app.database

import androidx.room.*
import com.mentalmachines.travel_app.database.dao.TripDao
import com.mentalmachines.travel_app.database.dao.UsersDao
import com.mentalmachines.travel_app.database.entity.DetailsEntity
import com.mentalmachines.travel_app.database.entity.TripEntity
import com.mentalmachines.travel_app.database.entity.UserEntity

@Database(entities = [UserEntity::class, TripEntity::class, DetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val tripDao: TripDao
}


private lateinit var INSTANCE: AppDatabase

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
