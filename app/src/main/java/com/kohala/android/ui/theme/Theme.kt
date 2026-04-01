package com.kohala.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val XboxDarkColorScheme = darkColorScheme(
    primary = XboxGreen,
    onPrimary = TextPrimary,
    secondary = XboxGreenLight,
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
        colorScheme = XboxDarkColorScheme,
        typography = XboxTypography,
        content = content
    )
}
