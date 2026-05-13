package com.pryd.app.presentation.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light palette
private val LightPrimary = Color(0xFF1A56DB)
private val LightOnPrimary = Color(0xFFFFFFFF)
private val LightBackground = Color(0xFFFFFFFF)
private val LightOnBackground = Color(0xFF0F172A)
private val LightSurface = Color(0xFFF8FAFC)
private val LightOnSurface = Color(0xFF0F172A)
private val LightSurfaceVariant = Color(0xFFF1F5F9)
private val LightOnSurfaceVariant = Color(0xFF64748B)
private val LightOutline = Color(0xFFE2E8F0)

// Dark palette
private val DarkPrimary = Color(0xFF4F8CFF)
private val DarkOnPrimary = Color(0xFF0F172A)
private val DarkBackground = Color(0xFF0F172A)
private val DarkOnBackground = Color(0xFFF1F5F9)
private val DarkSurface = Color(0xFF1E293B)
private val DarkOnSurface = Color(0xFFF1F5F9)
private val DarkSurfaceVariant = Color(0xFF1E293B)
private val DarkOnSurfaceVariant = Color(0xFF94A3B8)
private val DarkOutline = Color(0xFF334155)

// Semantic colors (same in both themes)
val PriorityHigh = Color(0xFFDC2626)
val PriorityMedium = Color(0xFFD97706)
val PriorityLow = Color(0xFF6B7280)
val StatusPending = Color(0xFF6B7280)
val StatusInProgress = Color(0xFF2563EB)
val StatusCompleted = Color(0xFF16A34A)
val PomodoroFocus = Color(0xFFDC2626)
val PomodoroBreak = Color(0xFF16A34A)
val PomodoroGold = Color(0xFFF59E0B)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

@Composable
fun PrydTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

