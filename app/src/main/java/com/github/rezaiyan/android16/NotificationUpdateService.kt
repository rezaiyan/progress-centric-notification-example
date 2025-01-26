package com.github.rezaiyan.android16

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class NotificationUpdateService : Service() {

    companion object {
        private const val UPDATE_INTERVAL = 16L
        private const val TOTAL_DURATION = 30000L
        private const val NOTIFICATION_ID = 1001

        fun startService(context: Context) {
            context.startForegroundService(Intent(context, NotificationUpdateService::class.java))
        }
    }

    private var progress = 0

    private lateinit var notificationManager: NotificationManager
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationHelper.createNotificationChannel(notificationManager)
        startForeground(
            NOTIFICATION_ID, NotificationHelper.createNotification(
                context = applicationContext,
                title = "Getting ready...",
                message = "Please wait",
                progress = progress
            )
        )
        serviceScope.launch {
            startProgressUpdates()
                .collect { progressState ->
                    notificationManager.notify(
                        NOTIFICATION_ID, NotificationHelper.createNotification(
                            context = applicationContext,
                            title = progressState.title,
                            message = progressState.subtitle,
                            progress = progressState.progress
                        )
                    )
                }
        }

        return START_NOT_STICKY
    }

    private fun startProgressUpdates(): Flow<ProgressState> = flow {
        var elapsedTime = 0L

        while (elapsedTime < TOTAL_DURATION) {
            elapsedTime += UPDATE_INTERVAL
            val progress = (elapsedTime * 1000 / TOTAL_DURATION).toInt()

            val title = when {
                progress < 100 -> "Driver found!"
                progress < 500 -> "On the way"
                progress < 950 -> "Almost there!"
                else -> "Driver has arrived!"
            }

            val subtitle = when {
                progress < 100 -> "Your driver is preparing to leave."
                progress < 500 -> "Driver is on the way. Hang tight!"
                progress < 950 -> "Driver is nearby. Get ready!"
                else -> "Your driver is here. Please meet them!"
            }

            emit(ProgressState(progress, title, subtitle))
            delay(UPDATE_INTERVAL)
        }

        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
