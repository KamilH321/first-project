package com.example.firstproject.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.firstproject.Keys
import com.example.firstproject.MainActivity
import com.example.firstproject.utils.ResManager
import com.example.firstproject.R
import com.example.firstproject.notifications.ReplyReceiver.Companion.ACTION

class NotificationHandler(
    private val ctx: Context,
    private val resManager: ResManager
) {
    private val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val activeConfigs = mutableMapOf<Int, NotificationConfig>()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannels()
        }
    }

    private fun createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    MIN_CHANNEL_ID,
                    resManager.getString(R.string.priority_min),
                    NotificationManager.IMPORTANCE_MIN
                ),
                NotificationChannel(
                    LOW_CHANNEL_ID,
                    resManager.getString(R.string.priority_low),
                    NotificationManager.IMPORTANCE_LOW
                ),
                NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    resManager.getString(R.string.priority_default),
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                NotificationChannel(
                    HIGH_CHANNEL_ID,
                    resManager.getString(R.string.priority_high),
                    NotificationManager.IMPORTANCE_HIGH
                )
            )

            channels.forEach {
                notificationManager.deleteNotificationChannel(it.id)
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    @SuppressLint("NotificationPermission")
    fun showNotification(config: NotificationConfig){
        activeConfigs.put(config.id, config)

        val builder = NotificationCompat.Builder(ctx, getChannelIdForImportance(config.importance))
            .setSmallIcon(R.drawable.ic_outline_adjust_24)
            .setContentTitle(config.title)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(toCompatPriority(config.importance))

        config.content?.let { text ->
            builder.setContentText(text)
            if (config.isExpandLongText){
                builder.setStyle(NotificationCompat.BigTextStyle().bigText(text))
            }
        }

        if (config.openMainOnClick){
            val intent = Intent(ctx, MainActivity::class.java).apply {
                putExtra(Keys.NOTIFY_TITLE, config.title)
                putExtra(Keys.NOTIFY_CONTENT, config.content)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

            val pendingIntent = PendingIntent.getActivity(
                ctx, config.id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            builder.setContentIntent(pendingIntent)
        }

        if (config.enableInlineReply){
            val remoteInput = RemoteInput.Builder(Keys.REMOTE_INPUT_KEY)
                .setLabel(resManager.getString(R.string.remote_input_label))
                .build()

            val replyIntent = Intent(ctx, ReplyReceiver::class.java).apply {
                action = ACTION
                putExtra(EXTRA_NOTIFICATION_ID, config.id)
            }

            val replyPendingIntent = PendingIntent.getBroadcast(
                ctx, config.id,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_outline_arrow_back_2_24,
                resManager.getString(R.string.reply),
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()

            builder.addAction(replyAction)
        }

        notificationManager.notify(config.id, builder.build())
    }

    fun updateContentIfExists(id: Int, newText: String?): Boolean {
        val old = activeConfigs[id] ?: return false
        val newConfig = old.copy(content = newText)
        showNotification(newConfig)
        return true
    }


    fun cancelAll() {
        activeConfigs.clear()
        notificationManager.cancelAll()
    }

    private fun toCompatPriority(notificationImportance: NotificationImportance): Int {
        return notificationImportance.value
    }

    private fun getChannelIdForImportance(importance: NotificationImportance): String {
        return when (importance) {
            NotificationImportance.MIN -> MIN_CHANNEL_ID
            NotificationImportance.LOW -> LOW_CHANNEL_ID
            NotificationImportance.DEFAULT -> DEFAULT_CHANNEL_ID
            NotificationImportance.HIGH -> HIGH_CHANNEL_ID
        }
    }

    private companion object{
        const val MIN_CHANNEL_ID = "min_channel_id"
        const val LOW_CHANNEL_ID = "low_channel_id"
        const val DEFAULT_CHANNEL_ID = "default_channel_id"
        const val HIGH_CHANNEL_ID = "high_channel_id"
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
    }
}