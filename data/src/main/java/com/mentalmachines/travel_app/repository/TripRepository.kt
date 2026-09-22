package com.mentalmachines.travel_app.repository

import com.mentalmachines.travel_app.database.Resource
import com.mentalmachines.travel_app.database.entity.asDomainModel
import com.mentalmachines.travel_app.domain.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlinx.coroutines.flow.Flow

/*class TripRepository(private val database: TripDatabase) {
    val videos: LiveData<List<Trip>> = Transformations.map(database.tripDao.getVideos()) {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos is called");
            val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}*/



interface TripRepository {
    // Returns a Flow for real-time updates from a database
    fun getAllTrips(): Flow<Resource<List<Trip>>>

    fun getTripById(tripId: String): Flow<Resource<Trip>>

    suspend fun addTrip(trip: Trip): Result<Unit>

    suspend fun deleteTrip(tripId: String): Result<Unit>

    suspend fun refreshTrips() // Force a network sync
}


class TripRepositoryImpl(
    // private val tripDao: TripDao,      // Local Source
    // private val tripService: TripApi,  // Remote Source
) : TripRepository {

    override fun getAllTrips(): Flow<Resource<List<Trip>>> = flow {
        emit(Resource.Loading)

        // Example logic:
        // 1. Try to get data from local DAO
        // 2. If empty or stale, fetch from API
        // 3. Emit the result
        try {
            // Mocking a delay
            kotlinx.coroutines.delay(1000)
            val mockList = listOf(
                Trip("1", "Paris", "5 Days"),
                Trip("2", "Tokyo", "10 Days")
            )
            emit(Resource.Success(mockList))
        } catch (e: Exception) {
            emit(Resource.Error("Could not load trips", e))
        }
    }

    override fun getTripById(tripId: String): Flow<Resource<Trip>> = flow {
        emit(Resource.Loading)
        // Implementation here...
    }

    override suspend fun addTrip(trip: Trip): Result<Unit> {
        return try {
            // tripDao.insert(trip)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTrip(tripId: String): Result<Unit> {
        return try {
            // tripDao.deleteById(tripId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshTrips() {
        // Logic to fetch from API and save to local Room DB
    }
}
