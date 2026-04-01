package com.sigand.kohala.installer

/**
 * Confirms the Vulkan layer is registered and loadable.
 * Wired in Step 3.
 */
class LayerValidator {

    fun isInstalled(): Boolean {
        // TODO: Step 3 — check if .so exists at install path
        return false
    }

    fun isLoadable(): Boolean {
        // TODO: Step 3 — quick Vulkan instance test
        return false
    }
}
