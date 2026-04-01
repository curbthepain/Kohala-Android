package com.sigand.kohala.installer

/**
 * Removes the Vulkan layer .so and JSON manifest from the install path.
 * Wired in Step 3.
 */
class Uninstaller {

    fun uninstall(): Result<Unit> {
        // TODO: Step 3 — remove .so and manifest, validate removal
        return Result.failure(NotImplementedError("Uninstaller not yet wired"))
    }
}
