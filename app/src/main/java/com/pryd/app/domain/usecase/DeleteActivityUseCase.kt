package com.pryd.app.domain.usecase

import com.pryd.app.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteById(id)
    }
}