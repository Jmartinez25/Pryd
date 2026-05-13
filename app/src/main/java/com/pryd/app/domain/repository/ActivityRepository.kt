package com.pryd.app.domain.repository

import com.pryd.app.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getByStatus(status: String): Flow<List<Activity>>
    suspend fun getById(id: String): Activity?
    suspend fun insert(activity: Activity)
    suspend fun updateStatus(id: String, status: String)
    suspend fun deleteById(id: String)
    suspend fun deleteAllCompleted()
}