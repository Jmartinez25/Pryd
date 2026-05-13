package com.pryd.app.domain.model

data class PomodoroSession(
    val id: String,
    val type: String,
    val durationMinutes: Int,
    val startTime: Long,
    val endTime: Long?,
    val isCompleted: Boolean
)