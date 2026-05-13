package com.pryd.app.presentation.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pryd.app.presentation.theme.PomodoroBreak
import com.pryd.app.presentation.theme.PomodoroFocus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen(
    viewModel: PomodoroViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val progress = if (uiState.totalTime > 0) {
        uiState.timeRemaining.toFloat() / uiState.totalTime.toFloat()
    } else 0f

    val timerColor = when (uiState.sessionType) {
        "FOCUS" -> PomodoroFocus
        "SHORT_BREAK", "LONG_BREAK" -> PomodoroBreak
        else -> PomodoroFocus
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pomodoro") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Session indicator
            Text(
                text = "Sesión ${uiState.currentSession}/4",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Circular timer
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(250.dp)
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 10.dp,
                    color = timerColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatTime(uiState.timeRemaining),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = when (uiState.sessionType) {
                            "FOCUS" -> "ENFOQUE"
                            "SHORT_BREAK" -> "DESCANSO CORTO"
                            "LONG_BREAK" -> "DESCANSO LARGO"
                            else -> ""
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = timerColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset
                OutlinedButton(onClick = { viewModel.reset() }) {
                    Text("↺")
                }

                // Play/Pause
                Button(
                    onClick = {
                        if (uiState.isRunning) viewModel.pause()
                        else viewModel.start()
                    },
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (uiState.isRunning) "⏸" else "▶",
                        fontSize = 24.sp
                    )
                }

                // Skip
                OutlinedButton(onClick = { viewModel.skip() }) {
                    Text("⏭")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Completed sessions today
            if (uiState.completedSessions > 0) {
                Text(
                    text = "🍅".repeat(uiState.completedSessions),
                    fontSize = 24.sp
                )
                Text(
                    text = "${uiState.completedSessions} pomodoros completados hoy",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}