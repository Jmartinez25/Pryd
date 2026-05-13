package com.pryd.app.data.repository

import com.pryd.app.data.local.dao.PomodoroSessionDao
import com.pryd.app.data.mapper.toDomain
import com.pryd.app.data.mapper.toEntity
import com.pryd.app.domain.model.PomodoroSession
import com.pryd.app.domain.repository.PomodoroSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PomodoroSessionRepositoryImpl @Inject constructor(
    private val dao: PomodoroSessionDao
) : PomodoroSessionRepository {

    override fun getAll(): Flow<List<PomodoroSession>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCompletedFocusSessionsForDay(startOfDay: Long, endOfDay: Long): List<PomodoroSession> {
        return dao.getCompletedFocusSessionsForDay(startOfDay, endOfDay).map { it.toDomain() }
    }

    override suspend fun insert(session: PomodoroSession) {
        dao.insert(session.toEntity())
    }

    override suspend fun updateSession(id: String, endTime: Long, isCompleted: Boolean) {
        dao.updateSession(id, endTime, isCompleted)
    }
}