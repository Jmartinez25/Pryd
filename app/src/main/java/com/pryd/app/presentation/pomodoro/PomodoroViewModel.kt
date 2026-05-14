package com.pryd.app.presentation.pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PomodoroUiState(
    val timeRemaining: Long = 25 * 60,
    val totalTime: Long = 25 * 60,
    val sessionType: String = "FOCUS",
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val completedSessions: Int = 0,
    val currentSession: Int = 1
)

@HiltViewModel
class PomodoroViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    private val focusDuration = 25 * 60L
    private val shortBreakDuration = 5 * 60L
    private val longBreakDuration = 15 * 60L

    fun start() {
        if (_uiState.value.isPaused) {
            resumeTimer()
        } else {
            startNewSession()
        }
    }

    private fun startNewSession() {
        val sessionType = _uiState.value.sessionType
        val duration = when (sessionType) {
            "FOCUS" -> focusDuration
            "SHORT_BREAK" -> shortBreakDuration
            "LONG_BREAK" -> longBreakDuration
            else -> focusDuration
        }

        _uiState.update {
            it.copy(
                timeRemaining = duration,
                totalTime = duration,
                isRunning = true,
                isPaused = false
            )
        }
        startCountdown()
    }

    private fun startCountdown() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0) {
                delay(1000L)
                _uiState.update { it.copy(timeRemaining = it.timeRemaining - 1) }
            }
            onSessionComplete()
        }
    }

    fun pause() {
        timerJob?.cancel()
        _uiState.update { it.copy(isRunning = false, isPaused = true) }
    }

    private fun resumeTimer() {
        _uiState.update { it.copy(isRunning = true, isPaused = false) }
        startCountdown()
    }

    fun reset() {
        timerJob?.cancel()
        val duration = _uiState.value.totalTime
        _uiState.update {
            it.copy(
                timeRemaining = duration,
                isRunning = false,
                isPaused = false
            )
        }
    }

    fun skip() {
        timerJob?.cancel()
        onSessionComplete()
    }

    private fun onSessionComplete() {
        val wasFocus = _uiState.value.sessionType == "FOCUS"
        val currentSession = _uiState.value.currentSession
        val completedSessions = if (wasFocus) _uiState.value.completedSessions + 1 else _uiState.value.completedSessions

        val nextType: String
        val nextSession: Int

        if (wasFocus) {
            if (currentSession % 4 == 0) {
                nextType = "LONG_BREAK"
            } else {
                nextType = "SHORT_BREAK"
            }
            nextSession = if (currentSession == 4) 1 else currentSession + 1
        } else {
            nextType = "FOCUS"
            nextSession = currentSession
        }

        val nextDuration = when (nextType) {
            "FOCUS" -> focusDuration
            "SHORT_BREAK" -> shortBreakDuration
            "LONG_BREAK" -> longBreakDuration
            else -> focusDuration
        }

        _uiState.update {
            it.copy(
                timeRemaining = nextDuration,
                totalTime = nextDuration,
                sessionType = nextType,
                isRunning = false,
                isPaused = false,
                completedSessions = completedSessions,
                currentSession = nextSession
            )
        }
    }
}