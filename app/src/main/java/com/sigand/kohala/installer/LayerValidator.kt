package com.sigand.kohala.installer

import java.io.File

class LayerValidator {

    private val soFile = File(LayerInstaller.LAYER_INSTALL_DIR, LayerInstaller.LAYER_SO)
    private val manifestFile = File(LayerInstaller.LAYER_INSTALL_DIR, LayerInstaller.LAYER_MANIFEST)

    fun isInstalled(): Boolean {
        return try {
            // Use su to check file existence since the path requires root
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "test -f ${soFile.absolutePath} && echo yes"))
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            output == "yes"
        } catch (_: Exception) {
            false
        }
    }

    fun hasManifest(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "test -f ${manifestFile.absolutePath} && echo yes"))
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            output == "yes"
        } catch (_: Exception) {
            false
        }
    }

    fun isLoadable(): Boolean {
        // Full Vulkan instance validation is deferred — for now, installed + manifest = loadable
        return isInstalled() && hasManifest()
    }
}
