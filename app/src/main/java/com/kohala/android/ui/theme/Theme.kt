package com.kohala.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val WindowsDarkColorScheme = darkColorScheme(
    primary = WindowsGreen,
    onPrimary = TextPrimary,
    secondary = WindowsGreenLight,
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
        colorScheme = WindowsDarkColorScheme,
        typography = WindowsTypography,
        content = content
    )
}
