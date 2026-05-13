package com.pryd.app.domain.repository

import com.pryd.app.domain.model.PomodoroSession
import kotlinx.coroutines.flow.Flow

interface PomodoroSessionRepository {
    fun getAll(): Flow<List<PomodoroSession>>
    suspend fun getCompletedFocusSessionsForDay(startOfDay: Long, endOfDay: Long): List<PomodoroSession>
    suspend fun insert(session: PomodoroSession)
    suspend fun updateSession(id: String, endTime: Long, isCompleted: Boolean)
}