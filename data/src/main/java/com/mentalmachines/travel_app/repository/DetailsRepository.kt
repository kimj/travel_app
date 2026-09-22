
package com.mentalmachines.travel_app.repository

import com.mentalmachines.travel_app.domain.Details
import com.mentalmachines.travel_app.database.AppDatabase
import com.mentalmachines.travel_app.database.entity.DetailsEntity
import com.mentalmachines.travel_app.database.entity.UserEntity
import com.mentalmachines.travel_app.database.entity.asDomainModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsApi: DetailsApi,
    private val appDatabase: AppDatabase
) {
    interface DetailsApi {
        fun getDetails(user: String)  : DetailsEntity{
            return TODO("Provide the return value")
        }
    }

    fun getUserDetails(user: String): Flow<Details?> =
            appDatabase.usersDao.getDetails(user).map { it?.asDomainModel() }

    fun refreshDetails(user: String) {
        try {
            val userDetails : DetailsEntity = detailsApi.getDetails(user)
            appDatabase.usersDao.insertDetails(userDetails)

        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    fun save(id: String) {}

}
