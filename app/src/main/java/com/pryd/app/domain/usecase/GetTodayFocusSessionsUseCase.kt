package com.pryd.app.domain.usecase

import com.pryd.app.domain.model.PomodoroSession
import com.pryd.app.domain.repository.PomodoroSessionRepository
import javax.inject.Inject

class GetTodayFocusSessionsUseCase @Inject constructor(
    private val repository: PomodoroSessionRepository
) {
    suspend operator fun invoke(startOfDay: Long, endOfDay: Long): List<PomodoroSession> {
        return repository.getCompletedFocusSessionsForDay(startOfDay, endOfDay)
    }
}