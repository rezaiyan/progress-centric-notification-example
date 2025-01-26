package com.github.rezaiyan.android16

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log

object NotificationHelper {

    private const val CHANNEL_ID = "example_channel_id"
    private const val CHANNEL_NAME = "Example Notifications"
    private const val CHANNEL_DESCRIPTION = "Notifications for example purposes"

    /**
     * Extension function to set a progress-centric style to a notification.
     */
    @SuppressLint("NewApi")
    private fun Notification.Builder.applyProgressStyle(
        context: Context,
        progress: Int,
    ): Notification.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            val progressStyle = Notification.ProgressStyle().apply {
                isStyledByProgress = true
                setProgress(progress)
                progressTrackerIcon = Icon.createWithResource(context, R.drawable.ic_car)
                progressSegments = listOf(
                    Notification.ProgressStyle.Segment(41).setColor(Color.BLACK),
                    Notification.ProgressStyle.Segment(552).setColor(Color.YELLOW),
                    Notification.ProgressStyle.Segment(253).setColor(Color.WHITE),
                    Notification.ProgressStyle.Segment(94).setColor(Color.BLUE)
                )
                progressPoints = listOf(
                    Notification.ProgressStyle.Point(60).setColor(Color.RED),
                    Notification.ProgressStyle.Point(560).setColor(Color.GREEN)
                )
            }
            this.setStyle(progressStyle)
        } else {
            Log.d("NotificationHelper", "ProgressStyle is not supported on this API level.")
        }
        return this
    }

    /**
     * Creates a notification channel if required by the Android version.
     */
    fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Creates a notification with a progress-centric style.
     */
    fun createNotification(
        context: Context,
        title: String,
        message: String,
        progress: Int,
    ): Notification {
        val fullScreenIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setFullScreenIntent(fullScreenIntent, true)
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .applyProgressStyle(context, progress)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(true)
            .build()
    }
}
