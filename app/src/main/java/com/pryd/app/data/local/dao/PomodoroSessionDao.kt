package com.pryd.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pryd.app.data.local.entity.PomodoroSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: PomodoroSessionEntity)

    @Query("SELECT * FROM pomodoro_sessions ORDER BY startTime DESC")
    fun getAll(): Flow<List<PomodoroSessionEntity>>

    @Query("SELECT * FROM pomodoro_sessions WHERE isCompleted = 1 AND type = 'FOCUS' AND startTime >= :startOfDay AND startTime <= :endOfDay")
    suspend fun getCompletedFocusSessionsForDay(startOfDay: Long, endOfDay: Long): List<PomodoroSessionEntity>

    @Query("UPDATE pomodoro_sessions SET endTime = :endTime, isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateSession(id: String, endTime: Long, isCompleted: Boolean)
}