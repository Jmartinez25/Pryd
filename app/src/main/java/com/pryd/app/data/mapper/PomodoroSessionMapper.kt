package com.pryd.app.data.mapper

import com.pryd.app.data.local.entity.PomodoroSessionEntity
import com.pryd.app.domain.model.PomodoroSession

fun PomodoroSessionEntity.toDomain(): PomodoroSession {
    return PomodoroSession(
        id = id,
        type = type,
        durationMinutes = durationMinutes,
        startTime = startTime,
        endTime = endTime,
        isCompleted = isCompleted
    )
}

fun PomodoroSession.toEntity(): PomodoroSessionEntity {
    return PomodoroSessionEntity(
        id = id,
        type = type,
        durationMinutes = durationMinutes,
        startTime = startTime,
        endTime = endTime,
        isCompleted = isCompleted
    )
}