package com.sigand.kohala.installer

import android.content.Context

/**
 * Copies libVkLayer_kohala.so to the layer install path and writes the JSON manifest.
 * Wired in Step 3.
 */
class LayerInstaller(private val context: Context) {

    companion object {
        const val LAYER_SO = "libVkLayer_kohala.so"
        const val LAYER_INSTALL_DIR = "/data/local/vulkan/implicit_layer.d"
    }

    fun install(): Result<Unit> {
        // TODO: Step 3 — copy .so from assets, write manifest, set permissions
        return Result.failure(NotImplementedError("Installer not yet wired"))
    }
}
