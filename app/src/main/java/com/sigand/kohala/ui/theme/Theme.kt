package com.sigand.kohala.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val KohalaDark = darkColorScheme(
    primary = Color(0xFF7DD3FC),        // sky-300
    onPrimary = Color(0xFF003548),
    primaryContainer = Color(0xFF004D67),
    onPrimaryContainer = Color(0xFFC5E7FF),
    secondary = Color(0xFF86EFAC),       // green-300
    onSecondary = Color(0xFF003A1F),
    error = Color(0xFFFCA5A5),
    background = Color(0xFF0F172A),      // slate-900
    surface = Color(0xFF1E293B),         // slate-800
    surfaceVariant = Color(0xFF334155),  // slate-700
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFFCBD5E1)
)

private val KohalaLight = lightColorScheme(
    primary = Color(0xFF0369A1),         // sky-700
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF001E2D),
    secondary = Color(0xFF16A34A),        // green-600
    onSecondary = Color.White,
    error = Color(0xFFDC2626),
    background = Color(0xFFF8FAFC),       // slate-50
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9),   // slate-100
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun KohalaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> KohalaDark
        else -> KohalaLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
