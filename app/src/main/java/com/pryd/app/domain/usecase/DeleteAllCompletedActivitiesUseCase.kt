package com.pryd.app.domain.usecase

import com.pryd.app.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteAllCompletedActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllCompleted()
    }
}