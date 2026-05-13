package com.pryd.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pomodoro_sessions")
data class PomodoroSessionEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val durationMinutes: Int,
    val startTime: Long,
    val endTime: Long?,
    val isCompleted: Boolean
)
