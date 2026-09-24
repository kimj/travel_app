package com.mentalmachines.travel_app.database

import androidx.room.*
import com.mentalmachines.travel_app.database.dao.InterestPlaceDao
import com.mentalmachines.travel_app.database.dao.ItineraryDao
import com.mentalmachines.travel_app.database.dao.PackItemDao
import com.mentalmachines.travel_app.database.dao.TransitStopDao
import com.mentalmachines.travel_app.database.dao.TripDao
import com.mentalmachines.travel_app.database.dao.UsersDao
import com.mentalmachines.travel_app.database.entity.DetailsEntity
import com.mentalmachines.travel_app.database.entity.InterestPlaceEntity
import com.mentalmachines.travel_app.database.entity.ItineraryItemEntity
import com.mentalmachines.travel_app.database.entity.PackItemEntity
import com.mentalmachines.travel_app.database.entity.TransitStopEntity
import com.mentalmachines.travel_app.database.entity.TripEntity
import com.mentalmachines.travel_app.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class, 
        TripEntity::class, 
        DetailsEntity::class, 
        PackItemEntity::class,
        ItineraryItemEntity::class,
        TransitStopEntity::class,
        InterestPlaceEntity::class
    ],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val tripDao: TripDao
    abstract val packItemDao: PackItemDao
    abstract val itineraryDao: ItineraryDao
    abstract val transitStopDao: TransitStopDao
    abstract val interestPlaceDao: InterestPlaceDao
}


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
