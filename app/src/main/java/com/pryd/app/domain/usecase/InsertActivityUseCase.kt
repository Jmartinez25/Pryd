package com.pryd.app.domain.usecase

import com.pryd.app.domain.model.Activity
import com.pryd.app.domain.repository.ActivityRepository
import javax.inject.Inject

class InsertActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(activity: Activity) {
        repository.insert(activity)
    }
}