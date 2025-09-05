package com.mentalmachines.travel_app.database

import androidx.room.*
import com.mentalmachines.travel_app.database.dao.UsersDao
import com.mentalmachines.travel_app.database.entity.DetailsEntity
import com.mentalmachines.travel_app.database.entity.UserEntity

@Database(entities = [UserEntity::class, DetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}
