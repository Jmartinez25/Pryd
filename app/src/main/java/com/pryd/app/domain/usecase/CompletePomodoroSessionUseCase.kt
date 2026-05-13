package com.pryd.app.domain.usecase

import com.pryd.app.domain.repository.PomodoroSessionRepository
import javax.inject.Inject

class CompletePomodoroSessionUseCase @Inject constructor(
    private val repository: PomodoroSessionRepository
) {
    suspend operator fun invoke(id: String, endTime: Long) {
        repository.updateSession(id, endTime, isCompleted = true)
    }
}