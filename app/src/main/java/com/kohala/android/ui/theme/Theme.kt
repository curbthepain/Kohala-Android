package com.kohala.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val KohalaDarkColorScheme = darkColorScheme(
    primary = AccentGreen,
    onPrimary = TextPrimary,
    secondary = AccentGreenLight,
    onSecondary = TextPrimary,
    background = DashboardBackground,
    onBackground = TextPrimary,
    surface = DashboardSurface,
    onSurface = TextPrimary,
    surfaceVariant = DashboardSurfaceLight,
    onSurfaceVariant = TextSecondary,
)

@Composable
fun KohalaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KohalaDarkColorScheme,
        typography = KohalaTypography,
        content = content
    )
}
