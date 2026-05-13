package com.pryd.app.data.repository

import com.pryd.app.data.local.dao.ActivityDao
import com.pryd.app.data.mapper.toDomain
import com.pryd.app.data.mapper.toEntity
import com.pryd.app.domain.model.Activity
import com.pryd.app.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val dao: ActivityDao
) : ActivityRepository {

    override fun getByStatus(status: String): Flow<List<Activity>> {
        return dao.getByStatus(status).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: String): Activity? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun insert(activity: Activity) {
        dao.insert(activity.toEntity())
    }

    override suspend fun updateStatus(id: String, status: String) {
        dao.updateStatus(id, status)
    }

    override suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }

    override suspend fun deleteAllCompleted() {
        dao.deleteAllCompleted()
    }
}