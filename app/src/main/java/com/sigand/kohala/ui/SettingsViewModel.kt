package com.sigand.kohala.ui

import android.app.Application
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sigand.kohala.installer.LayerConfig
import com.sigand.kohala.installer.LayerInstaller
import com.sigand.kohala.installer.QualityPreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SettingsUiState(
    val selectedPreset: QualityPreset = QualityPreset.BALANCED,
    val presets: List<QualityPreset> = QualityPreset.entries,
    val gameOverrides: Map<String, QualityPreset> = emptyMap(),
    val logText: String = ""
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val config = LayerConfig(application)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                selectedPreset = config.getPreset(),
                gameOverrides = config.getGameOverrides()
            )
        }
    }

    fun selectPreset(preset: QualityPreset) {
        viewModelScope.launch(Dispatchers.IO) {
            config.setPreset(preset)
            _uiState.value = _uiState.value.copy(selectedPreset = preset)
        }
    }

    fun setGameOverride(packageName: String, preset: QualityPreset) {
        viewModelScope.launch(Dispatchers.IO) {
            config.setGameOverride(packageName, preset)
            _uiState.value = _uiState.value.copy(gameOverrides = config.getGameOverrides())
        }
    }

    fun removeGameOverride(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            config.removeGameOverride(packageName)
            _uiState.value = _uiState.value.copy(gameOverrides = config.getGameOverrides())
        }
    }

    fun exportLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            val app = getApplication<Application>()
            val logContent = collectLogs()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val logFile = File(app.cacheDir, "kohala_logs_$timestamp.txt")
            logFile.writeText(logContent)

            val uri = FileProvider.getUriForFile(app, "${app.packageName}.fileprovider", logFile)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            app.startActivity(Intent.createChooser(shareIntent, "Export Kohala Logs").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    private fun collectLogs(): String {
        val sb = StringBuilder()
        sb.appendLine("=== Kohala Layer Logs ===")
        sb.appendLine("Timestamp: ${Date()}")
        sb.appendLine("Config preset: ${config.getPreset().label}")
        sb.appendLine("Layer enabled: ${config.isLayerEnabled()}")
        sb.appendLine()

        // Collect logcat lines related to Vulkan/Kohala
        try {
            val process = Runtime.getRuntime().exec(arrayOf("logcat", "-d", "-t", "500", "-s", "Vulkan:*", "Kohala:*"))
            sb.appendLine("--- Logcat (Vulkan/Kohala) ---")
            sb.appendLine(process.inputStream.bufferedReader().readText())
            process.waitFor()
        } catch (e: Exception) {
            sb.appendLine("Could not collect logcat: ${e.message}")
        }

        // Check layer status
        sb.appendLine()
        sb.appendLine("--- Layer Files ---")
        try {
            val soPath = "${LayerInstaller.LAYER_INSTALL_DIR}/${LayerInstaller.LAYER_SO}"
            val manifestPath = "${LayerInstaller.LAYER_INSTALL_DIR}/${LayerInstaller.LAYER_MANIFEST}"
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "ls -la $soPath $manifestPath 2>&1"))
            sb.appendLine(process.inputStream.bufferedReader().readText())
            process.waitFor()
        } catch (e: Exception) {
            sb.appendLine("Could not check layer files: ${e.message}")
        }

        return sb.toString()
    }
}
