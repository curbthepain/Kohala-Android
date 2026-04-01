package com.sigand.kohala.installer

import android.content.Context
import java.security.MessageDigest

enum class LicenseTier(val label: String) {
    COMMUNITY("Community"),
    PRO("Pro"),
    STUDIO("Studio")
}

class LicenseValidator(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "kohala_license"
        private const val KEY_LICENSE = "license_key"
        private const val COMMUNITY_PREFIX = "KOHALA-COMM-"
        private const val PRO_PREFIX = "KOHALA-PRO-"
        private const val STUDIO_PREFIX = "KOHALA-STUDIO-"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCurrentTier(): LicenseTier {
        val key = prefs.getString(KEY_LICENSE, null) ?: return LicenseTier.COMMUNITY
        return validateKey(key) ?: LicenseTier.COMMUNITY
    }

    fun activateKey(key: String): Result<LicenseTier> {
        val tier = validateKey(key)
            ?: return Result.failure(IllegalArgumentException("Invalid license key"))
        prefs.edit().putString(KEY_LICENSE, key).apply()
        return Result.success(tier)
    }

    fun deactivate() {
        prefs.edit().remove(KEY_LICENSE).apply()
    }

    fun hasKey(): Boolean {
        return prefs.getString(KEY_LICENSE, null) != null
    }

    private fun validateKey(key: String): LicenseTier? {
        val trimmed = key.trim().uppercase()
        val tier = when {
            trimmed.startsWith(STUDIO_PREFIX) -> LicenseTier.STUDIO
            trimmed.startsWith(PRO_PREFIX) -> LicenseTier.PRO
            trimmed.startsWith(COMMUNITY_PREFIX) -> LicenseTier.COMMUNITY
            else -> return null
        }
        // Validate checksum: last 8 chars should be first 8 of SHA-256 of the prefix + body
        val parts = trimmed.split("-")
        if (parts.size < 4) return null
        val checksum = parts.last()
        val body = parts.dropLast(1).joinToString("-")
        val expected = sha256(body).take(8).uppercase()
        if (checksum != expected) return null
        return tier
    }

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}
