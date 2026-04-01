package com.sigand.kohala.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Background service that monitors layer health.
 * Wired in Step 6.
 */
class LayerStatusService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: Step 6 — periodic health check, notification on failure
        return START_NOT_STICKY
    }
}
