package com.pryd.app.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pryd.app.domain.model.Activity
import com.pryd.app.domain.usecase.InsertActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val insertActivity: InsertActivityUseCase
) : ViewModel() {

    private val _savedEvent = MutableSharedFlow<Boolean>()
    val savedEvent: SharedFlow<Boolean> = _savedEvent

    fun save(title: String, description: String, priority: String, dueDate: Long?) {
        viewModelScope.launch {
            val activity = Activity(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description.ifBlank { null },
                priority = priority,
                status = "PENDING",
                dueDate = dueDate,
                createdAt = System.currentTimeMillis(),
                completedAt = null
            )
            insertActivity(activity)
            _savedEvent.emit(true)
        }
    }
}