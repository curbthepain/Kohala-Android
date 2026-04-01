package com.sigand.kohala.installer

import android.content.Context
import java.io.File

class LayerInstaller(private val context: Context) {

    companion object {
        const val LAYER_SO = "libVkLayer_kohala.so"
        const val LAYER_MANIFEST = "VkLayer_kohala.json"
        const val LAYER_INSTALL_DIR = "/data/local/vulkan/implicit_layer.d"
        const val LAYER_LIB_NAME = "VK_LAYER_SIGAND_kohala"
    }

    fun install(): Result<Unit> = runCatching {
        val installDir = File(LAYER_INSTALL_DIR)
        val soFile = File(installDir, LAYER_SO)
        val manifestFile = File(installDir, LAYER_MANIFEST)

        // Stage files to app cache first, then su-copy to system path
        val cachedSo = stageSoFromAssets()
        val cachedManifest = stageManifest(soFile.absolutePath)

        val commands = listOf(
            "mkdir -p $LAYER_INSTALL_DIR",
            "cp ${cachedSo.absolutePath} ${soFile.absolutePath}",
            "cp ${cachedManifest.absolutePath} ${manifestFile.absolutePath}",
            "chmod 644 ${soFile.absolutePath}",
            "chmod 644 ${manifestFile.absolutePath}"
        )

        executeRootCommands(commands)

        // Validate after install
        val validator = LayerValidator()
        check(validator.isInstalled()) { "Layer .so not found after install" }
    }

    private fun stageSoFromAssets(): File {
        val cacheFile = File(context.cacheDir, LAYER_SO)
        context.assets.open(LAYER_SO).use { input ->
            cacheFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return cacheFile
    }

    private fun stageManifest(soPath: String): File {
        val manifest = """
            {
                "file_format_version": "1.0.0",
                "layer": {
                    "name": "$LAYER_LIB_NAME",
                    "type": "GLOBAL",
                    "library_path": "$soPath",
                    "api_version": "1.1.0",
                    "implementation_version": "1",
                    "description": "Kohala Vulkan Layer by Sigand, Inc."
                }
            }
        """.trimIndent()

        val cacheFile = File(context.cacheDir, LAYER_MANIFEST)
        cacheFile.writeText(manifest)
        return cacheFile
    }
}

internal fun executeRootCommands(commands: List<String>) {
    val process = Runtime.getRuntime().exec("su")
    process.outputStream.bufferedWriter().use { writer ->
        for (cmd in commands) {
            writer.write(cmd)
            writer.newLine()
        }
        writer.write("exit")
        writer.newLine()
    }
    val exitCode = process.waitFor()
    check(exitCode == 0) {
        val stderr = process.errorStream.bufferedReader().readText()
        "Root command failed (exit $exitCode): $stderr"
    }
}
