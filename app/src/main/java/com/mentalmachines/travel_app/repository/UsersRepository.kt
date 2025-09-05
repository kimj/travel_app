package com.mentalmachines.travel_app.repository


import com.mentalmachines.compose.domain.User
import com.mentalmachines.travel_app.database.AppDatabase
import com.mentalmachines.travel_app.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersApi: UsersApi,
    private val appDatabase: AppDatabase
) {

    class UsersApi {
        fun getUsers() : List<User>{
            return listOf(User(
                id = 0,
                avatar = "avatar",
                username = "username"
            ))
        }
    }

    val users: Flow<List<UserEntity>?> =
        appDatabase.usersDao.getUsers()//.map { it?.asDomainModel() }

    suspend fun refreshUsers() {
        try {
            val users = usersApi.getUsers()
            // appDatabase.usersDao.insertUsers(users)
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}