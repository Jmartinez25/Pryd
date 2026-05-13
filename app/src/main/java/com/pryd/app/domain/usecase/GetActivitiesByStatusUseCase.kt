package com.pryd.app.domain.usecase

import com.pryd.app.domain.model.Activity
import com.pryd.app.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesByStatusUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(status: String): Flow<List<Activity>> {
        return repository.getByStatus(status)
    }
}