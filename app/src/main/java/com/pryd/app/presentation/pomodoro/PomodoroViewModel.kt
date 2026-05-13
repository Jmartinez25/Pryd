package com.pryd.app.presentation.pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pryd.app.domain.model.PomodoroSession
import com.pryd.app.domain.usecase.CompletePomodoroSessionUseCase
import com.pryd.app.domain.usecase.GetTodayFocusSessionsUseCase
import com.pryd.app.domain.usecase.InsertPomodoroSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class PomodoroUiState(
    val timeRemaining: Long = 25 * 60,          // in seconds
    val totalTime: Long = 25 * 60,
    val sessionType: String = "FOCUS",
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val completedSessions: Int = 0,
    val currentSession: Int = 1               // 1 to 4
)

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val insertSession: InsertPomodoroSessionUseCase,
    private val completeSession: CompletePomodoroSessionUseCase,
    private val getTodayFocusSessions: GetTodayFocusSessionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentSessionId: String? = null

    private val focusDuration = 25 * 60L
    private val shortBreakDuration = 5 * 60L
    private val longBreakDuration = 15 * 60L

    init {
        loadTodaySessions()
    }

    private fun loadTodaySessions() {
        viewModelScope.launch {
            val start = getStartOfDay()
            val end = getEndOfDay()
            val sessions = getTodayFocusSessions(start, end)
            _uiState.update {
                it.copy(completedSessions = sessions.size)
            }
        }
    }

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

        val sessionId = UUID.randomUUID().toString()
        currentSessionId = sessionId

        viewModelScope.launch {
            insertSession(
                PomodoroSession(
                    id = sessionId,
                    type = sessionType,
                    durationMinutes = (duration / 60).toInt(),
                    startTime = System.currentTimeMillis(),
                    endTime = null,
                    isCompleted = false
                )
            )
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
        currentSessionId?.let { id ->
            viewModelScope.launch {
                completeSession(id, System.currentTimeMillis())
            }
        }

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

    private fun getStartOfDay(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun getEndOfDay(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        return cal.timeInMillis
    }
}