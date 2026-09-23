package com.mentalmachines.travel_app.repository

import com.mentalmachines.travel_app.database.Resource
import com.mentalmachines.travel_app.database.dao.TripDao
import com.mentalmachines.travel_app.database.entity.asDomainModel
import com.mentalmachines.travel_app.database.entity.asEntity
import com.mentalmachines.travel_app.domain.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.GET
import timber.log.Timber
import javax.inject.Inject

interface TripApi {
    @GET("trips")
    suspend fun getTrips(): List<Trip>
}

interface TripRepository {
    fun getAllTrips(): Flow<Resource<List<Trip>>>

    fun getTripById(tripId: String): Flow<Resource<Trip>>

    suspend fun addTrip(trip: Trip): Result<Unit>

    suspend fun deleteTrip(tripId: String): Result<Unit>

    suspend fun refreshTrips()
}

class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao,
    private val tripService: TripApi,
) : TripRepository {

    override fun getAllTrips(): Flow<Resource<List<Trip>>> = flow {
        emit(Resource.Loading)
        try {
            tripDao.getAllTrips().collect { entities ->
                emit(Resource.Success(entities.asDomainModel()))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error loading trips")
            emit(Resource.Error("Could not load trips", e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getTripById(tripId: String): Flow<Resource<Trip>> = flow {
        emit(Resource.Loading)
        try {
            tripDao.getTripById(tripId).collect { entity ->
                if (entity != null) {
                    emit(Resource.Success(entity.asDomainModel()))
                } else {
                    emit(Resource.Error("Trip not found"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error loading trip with id $tripId")
            emit(Resource.Error("Could not load trip", e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addTrip(trip: Trip): Result<Unit> {
        return try {
            tripDao.insert(trip.asEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error adding trip")
            Result.failure(e)
        }
    }

    override suspend fun deleteTrip(tripId: String): Result<Unit> {
        return try {
            tripDao.deleteById(tripId)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting trip")
            Result.failure(e)
        }
    }

    override suspend fun refreshTrips() {
        try {
            val remoteTrips = tripService.getTrips()
            tripDao.insertAll(remoteTrips.map { it.asEntity() })
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing trips from remote API")
        }
    }
}
