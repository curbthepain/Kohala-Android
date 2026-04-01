package com.sigand.kohala.installer

import java.io.File

class Uninstaller {

    fun uninstall(): Result<Unit> = runCatching {
        val soFile = File(LayerInstaller.LAYER_INSTALL_DIR, LayerInstaller.LAYER_SO)
        val manifestFile = File(LayerInstaller.LAYER_INSTALL_DIR, LayerInstaller.LAYER_MANIFEST)

        val commands = listOf(
            "rm -f ${soFile.absolutePath}",
            "rm -f ${manifestFile.absolutePath}"
        )

        executeRootCommands(commands)

        val validator = LayerValidator()
        check(!validator.isInstalled()) { "Layer .so still present after uninstall" }
    }
}
