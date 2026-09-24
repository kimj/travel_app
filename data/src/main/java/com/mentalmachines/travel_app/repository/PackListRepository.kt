package com.mentalmachines.travel_app.repository

import com.mentalmachines.travel_app.database.dao.PackItemDao
import com.mentalmachines.travel_app.database.entity.asDomainModel
import com.mentalmachines.travel_app.domain.PackItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PackListRepository {
    fun getPackItems(): Flow<List<PackItem>>
    suspend fun togglePacked(id: String, isPacked: Boolean)
    suspend fun seedInitialItemsIfEmpty()
}

class PackListRepositoryImpl @Inject constructor(
    private val packItemDao: PackItemDao
) : PackListRepository {

    override fun getPackItems(): Flow<List<PackItem>> {
        return packItemDao.getAllPackItems()
            .map { entities -> entities.asDomainModel() }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun togglePacked(id: String, isPacked: Boolean) {
        withContext(Dispatchers.IO) {
            packItemDao.updatePackedStatus(id, isPacked)
        }
    }

    override suspend fun seedInitialItemsIfEmpty() {
        // Prepopulation is handled directly by Room DatabaseCallback using assets
    }
}
