package com.sigand.kohala.installer

import android.content.Context
import org.json.JSONObject
import java.io.File

enum class QualityPreset(val key: String, val label: String) {
    PERFORMANCE("performance", "Performance"),
    BALANCED("balanced", "Balanced"),
    QUALITY("quality", "Quality")
}

class LayerConfig(private val context: Context) {

    companion object {
        private const val CONFIG_FILE = "kohala_config.json"
        private const val SYSTEM_CONFIG_PATH = "/data/local/vulkan/implicit_layer.d/$CONFIG_FILE"
    }

    private val localFile = File(context.filesDir, CONFIG_FILE)

    fun getPreset(): QualityPreset {
        val json = readConfig()
        val key = json.optString("quality_preset", QualityPreset.BALANCED.key)
        return QualityPreset.entries.find { it.key == key } ?: QualityPreset.BALANCED
    }

    fun setPreset(preset: QualityPreset) {
        val json = readConfig()
        json.put("quality_preset", preset.key)
        writeConfig(json)
    }

    fun isLayerEnabled(): Boolean {
        return readConfig().optBoolean("enabled", false)
    }

    fun setLayerEnabled(enabled: Boolean) {
        val json = readConfig()
        json.put("enabled", enabled)
        writeConfig(json)
    }

    private fun readConfig(): JSONObject {
        return try {
            if (localFile.exists()) {
                JSONObject(localFile.readText())
            } else {
                defaultConfig()
            }
        } catch (_: Exception) {
            defaultConfig()
        }
    }

    private fun writeConfig(json: JSONObject) {
        localFile.writeText(json.toString(2))
        // Push to system path if rooted
        try {
            executeRootCommands(listOf("cp ${localFile.absolutePath} $SYSTEM_CONFIG_PATH"))
        } catch (_: Exception) {
            // Non-fatal — local config still saved
        }
    }

    fun getGameOverrides(): Map<String, QualityPreset> {
        val json = readConfig()
        val overrides = json.optJSONObject("game_overrides") ?: return emptyMap()
        val result = mutableMapOf<String, QualityPreset>()
        for (key in overrides.keys()) {
            val preset = QualityPreset.entries.find { it.key == overrides.optString(key) }
            if (preset != null) result[key] = preset
        }
        return result
    }

    fun setGameOverride(packageName: String, preset: QualityPreset) {
        val json = readConfig()
        val overrides = json.optJSONObject("game_overrides") ?: org.json.JSONObject()
        overrides.put(packageName, preset.key)
        json.put("game_overrides", overrides)
        writeConfig(json)
    }

    fun removeGameOverride(packageName: String) {
        val json = readConfig()
        val overrides = json.optJSONObject("game_overrides") ?: return
        overrides.remove(packageName)
        json.put("game_overrides", overrides)
        writeConfig(json)
    }

    private fun defaultConfig(): JSONObject {
        return JSONObject().apply {
            put("quality_preset", QualityPreset.BALANCED.key)
            put("enabled", false)
            put("game_overrides", org.json.JSONObject())
        }
    }
}
