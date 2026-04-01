package com.sigand.kohala.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sigand.kohala.installer.LicenseValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AboutUiState(
    val currentTier: String = "Community",
    val licenseKeyInput: String = "",
    val licenseError: String? = null,
    val hasActiveKey: Boolean = false
)

class AboutViewModel(application: Application) : AndroidViewModel(application) {

    private val license = LicenseValidator(application)

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState

    init {
        val tier = license.getCurrentTier()
        _uiState.value = _uiState.value.copy(
            currentTier = tier.label,
            hasActiveKey = license.hasKey()
        )
    }

    fun updateKeyInput(key: String) {
        _uiState.value = _uiState.value.copy(licenseKeyInput = key, licenseError = null)
    }

    fun activateKey() {
        val key = _uiState.value.licenseKeyInput
        license.activateKey(key)
            .onSuccess { tier ->
                _uiState.value = _uiState.value.copy(
                    currentTier = tier.label,
                    licenseError = null,
                    hasActiveKey = true,
                    licenseKeyInput = ""
                )
            }
            .onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    licenseError = e.message ?: "Invalid key"
                )
            }
    }

    fun deactivate() {
        license.deactivate()
        _uiState.value = _uiState.value.copy(
            currentTier = "Community",
            hasActiveKey = false
        )
    }
}
