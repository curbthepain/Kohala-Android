# Kohala ProGuard rules

# Keep Compose
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }

# Keep Kotlin metadata for reflection
-keepattributes RuntimeVisibleAnnotations

# Keep the installer package (uses Runtime.exec)
-keep class com.sigand.kohala.installer.** { *; }

# Keep the service
-keep class com.sigand.kohala.service.** { *; }
