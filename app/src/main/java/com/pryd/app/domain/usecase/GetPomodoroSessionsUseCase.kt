package com.pryd.app.domain.usecase

import com.pryd.app.domain.model.PomodoroSession
import com.pryd.app.domain.repository.PomodoroSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPomodoroSessionsUseCase @Inject constructor(
    private val repository: PomodoroSessionRepository
) {
    operator fun invoke(): Flow<List<PomodoroSession>> {
        return repository.getAll()
    }
}