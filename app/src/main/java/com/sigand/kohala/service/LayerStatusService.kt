package com.sigand.kohala.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.sigand.kohala.installer.LayerValidator

class LayerStatusService : Service() {

    companion object {
        private const val CHANNEL_ID = "kohala_layer_status"
        private const val FOREGROUND_CHANNEL_ID = "kohala_foreground"
        private const val NOTIFICATION_ID = 1001
        private const val FOREGROUND_NOTIFICATION_ID = 1002
        private const val CHECK_INTERVAL_MS = 60_000L

        fun start(context: Context) {
            context.startForegroundService(Intent(context, LayerStatusService::class.java))
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, LayerStatusService::class.java))
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private val validator = LayerValidator()
    private var wasHealthy = true

    private val checkRunnable = object : Runnable {
        override fun run() {
            checkLayerHealth()
            handler.postDelayed(this, CHECK_INTERVAL_MS)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        startInForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(checkRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(checkRunnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startInForeground() {
        val notification = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentTitle("Kohala")
            .setContentText("Monitoring Vulkan layer health")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        ServiceCompat.startForeground(
            this,
            FOREGROUND_NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
        )
    }

    private fun checkLayerHealth() {
        val installed = validator.isInstalled()
        val loadable = validator.isLoadable()
        val healthy = installed && loadable

        if (wasHealthy && !healthy && installed) {
            showUnhealthyNotification()
        }

        wasHealthy = healthy
    }

    private fun showUnhealthyNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Kohala Layer Issue")
            .setContentText("The Vulkan layer is installed but may not be loading correctly.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannels() {
        val manager = getSystemService(NotificationManager::class.java)

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                "Layer Status Alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications about Kohala Vulkan layer health issues"
            }
        )

        manager.createNotificationChannel(
            NotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "Background Monitoring",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Persistent notification while monitoring layer health"
            }
        )
    }
}
