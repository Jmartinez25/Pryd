package com.pryd.app.presentation.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pryd.app.domain.model.Activity
import com.pryd.app.domain.usecase.DeleteActivityUseCase
import com.pryd.app.domain.usecase.DeleteAllCompletedActivitiesUseCase
import com.pryd.app.domain.usecase.GetActivitiesByStatusUseCase
import com.pryd.app.domain.usecase.UpdateActivityStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BoardUiState(
    val pending: List<Activity> = emptyList(),
    val inProgress: List<Activity> = emptyList(),
    val completed: List<Activity> = emptyList()
)

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getActivitiesByStatus: GetActivitiesByStatusUseCase,
    private val updateActivityStatus: UpdateActivityStatusUseCase,
    private val deleteActivity: DeleteActivityUseCase,
    private val deleteAllCompleted: DeleteAllCompletedActivitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState: StateFlow<BoardUiState> = _uiState.asStateFlow()

    init {
        observeActivities()
    }

    private fun observeActivities() {
        viewModelScope.launch {
            combine(
                getActivitiesByStatus("PENDING"),
                getActivitiesByStatus("IN_PROGRESS"),
                getActivitiesByStatus("COMPLETED")
            ) { pending, inProgress, completed ->
                BoardUiState(
                    pending = pending,
                    inProgress = inProgress,
                    completed = completed
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun moveToInProgress(id: String) {
        viewModelScope.launch {
            updateActivityStatus(id, "IN_PROGRESS")
        }
    }

    fun moveToCompleted(id: String) {
        viewModelScope.launch {
            updateActivityStatus(id, "COMPLETED")
        }
    }

    fun deleteActivity(id: String) {
        viewModelScope.launch {
            deleteActivity(id)
        }
    }

    fun clearCompleted() {
        viewModelScope.launch {
            deleteAllCompleted()
        }
    }
}