package com.sigand.kohala.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sigand.kohala.installer.LayerConfig
import com.sigand.kohala.installer.LayerInstaller
import com.sigand.kohala.installer.LayerValidator
import com.sigand.kohala.installer.Uninstaller
import com.sigand.kohala.service.LayerStatusService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LayerUiState(
    val installed: Boolean = false,
    val enabled: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val qualityPreset: String = "Balanced"
)

class LayerViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application
    private val installer = LayerInstaller(application)
    private val validator = LayerValidator()
    private val uninstaller = Uninstaller()
    private val config = LayerConfig(application)

    private val _uiState = MutableStateFlow(LayerUiState())
    val uiState: StateFlow<LayerUiState> = _uiState

    init {
        refreshStatus()
    }

    fun refreshStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val installed = validator.isInstalled()
            val enabled = config.isLayerEnabled()
            val preset = config.getPreset()
            _uiState.value = _uiState.value.copy(
                installed = installed,
                enabled = enabled,
                qualityPreset = preset.label,
                error = null
            )
        }
    }

    fun install() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            installer.install()
                .onSuccess {
                    config.setLayerEnabled(true)
                    _uiState.value = _uiState.value.copy(
                        installed = true,
                        enabled = true,
                        loading = false
                    )
                    LayerStatusService.start(app)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        error = e.message ?: "Install failed"
                    )
                }
        }
    }

    fun uninstall() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            uninstaller.uninstall()
                .onSuccess {
                    config.setLayerEnabled(false)
                    _uiState.value = _uiState.value.copy(
                        installed = false,
                        enabled = false,
                        loading = false
                    )
                    LayerStatusService.stop(app)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        error = e.message ?: "Uninstall failed"
                    )
                }
        }
    }

    fun setEnabled(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            config.setLayerEnabled(enabled)
            _uiState.value = _uiState.value.copy(enabled = enabled)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
