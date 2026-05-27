package com.example.firstproject

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify

class AppMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.isNotEmpty()) {
            val kind = message.data[KEY_KIND]
            val title = message.data[KEY_TITLE] ?: DEFAULT_MESSAGE
            val messageText = message.data[KEY_MESSAGE] ?: ""
            sendNotification(kind, title, messageText)
        }
    }

    private fun sendNotification(kind: String?, title: String, messageText: String) {

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
        val notificationId = System.currentTimeMillis().toInt()

        val builder = when (kind) {
            "auth" -> {
                NotificationCompat.Builder(this, AUTH_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setCategory(Notification.CATEGORY_STATUS)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
            }
            "promo" -> {
                NotificationCompat.Builder(this, PROMO_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setCategory(Notification.CATEGORY_PROMO)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(messageText))
            }
            else -> {
                NotificationCompat.Builder(this, PROMO_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
            }
        }

        val notification = builder
            .setContentTitle(title)
            .setContentText(messageText)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private companion object {
        private const val PROMO_CHANNEL = "promo_chanel"
        private const val AUTH_CHANNEL = "auth_chanel"

        private const val DEFAULT_MESSAGE = "Стандартный заголовок"

        const val KEY_KIND = "kind"
        const val KEY_TITLE = "title"
        const val KEY_MESSAGE = "message"
    }
}