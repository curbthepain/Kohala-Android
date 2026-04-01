package com.sigand.kohala.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sigand.kohala.installer.LayerConfig
import com.sigand.kohala.installer.QualityPreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val selectedPreset: QualityPreset = QualityPreset.BALANCED,
    val presets: List<QualityPreset> = QualityPreset.entries
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val config = LayerConfig(application)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        _uiState.value = _uiState.value.copy(selectedPreset = config.getPreset())
    }

    fun selectPreset(preset: QualityPreset) {
        viewModelScope.launch(Dispatchers.IO) {
            config.setPreset(preset)
            _uiState.value = _uiState.value.copy(selectedPreset = preset)
        }
    }

    fun exportLogs() {
        // TODO: collect layer logs and share via intent
        Toast.makeText(getApplication(), "Log export not yet implemented", Toast.LENGTH_SHORT).show()
    }
}
