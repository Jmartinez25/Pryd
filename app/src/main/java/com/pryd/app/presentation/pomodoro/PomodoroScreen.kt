package com.pryd.app.presentation.pomodoro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pryd.app.presentation.theme.PomodoroBreak
import com.pryd.app.presentation.theme.PomodoroFocus
import com.pryd.app.presentation.theme.StatusPending
import kotlin.math.cos
import kotlin.math.sin

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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(250.dp)
                ) {
                    PomodoroTimerArc(
                        progress = { progress },
                        timerColor = { timerColor },
                        modifier = Modifier.fillMaxSize()
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
                                "FOCUS" -> "ENFÓCATE"
                                "SHORT_BREAK" -> "DESCANSO CORTO"
                                "LONG_BREAK" -> "DESCANSO LARGO"
                                else -> ""
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = timerColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedButton(
                    onClick = { viewModel.reset() },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF78909C))
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Reiniciar",
                        tint = Color(0xFF78909C)
                    )
                }

                Button(
                    onClick = {
                        if (uiState.isRunning) viewModel.pause()
                        else viewModel.start()
                    },
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = timerColor,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = if (uiState.isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (uiState.isRunning) "Pausar" else "Iniciar",
                        modifier = Modifier.size(28.dp)
                    )
                }

                    OutlinedButton(
                        onClick = { viewModel.skip() },
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF78909C))
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Saltar descanso",
                            tint = Color(0xFF78909C)
                        )
                    }

            }
        }
    }
}

@Composable
private fun PomodoroTimerArc(
    progress: () -> Float,
    timerColor: () -> Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val currentProgress = progress()
        val currentColor = timerColor()

        val strokeWidth = 10.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2
        val topLeft = Offset(
            (size.width - radius * 2) / 2,
            (size.height - radius * 2) / 2
        )
        val arcSize = Size(radius * 2, radius * 2)

        drawArc(
            color = StatusPending,
            startAngle = -90f,
            sweepAngle = -360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = currentColor,
            startAngle = -90f,
            sweepAngle = -360f * currentProgress,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        if (currentProgress > 0f) {
            val angleRad = Math.toRadians((-90.0 + -360.0 * currentProgress))
            val thumbX = center.x + radius * cos(angleRad).toFloat()
            val thumbY = center.y + radius * sin(angleRad).toFloat()

            drawCircle(
                color = currentColor,
                radius = strokeWidth / 2 + 4.dp.toPx(),
                center = Offset(thumbX, thumbY)
            )
        }
    }
}

private fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}