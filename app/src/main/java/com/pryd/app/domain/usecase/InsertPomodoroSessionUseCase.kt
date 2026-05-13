package com.pryd.app.domain.usecase

import com.pryd.app.domain.model.PomodoroSession
import com.pryd.app.domain.repository.PomodoroSessionRepository
import javax.inject.Inject

class InsertPomodoroSessionUseCase @Inject constructor(
    private val repository: PomodoroSessionRepository
) {
    suspend operator fun invoke(session: PomodoroSession) {
        repository.insert(session)
    }
}