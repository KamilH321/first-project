package com.example.firstproject.notifications

import androidx.core.app.NotificationCompat

data class NotificationConfig(
    val id: Int,
    val title: String,
    val content: String?,
    val isExpandLongText: Boolean,
    val importance: NotificationImportance,
    val openMainOnClick: Boolean,
    val enableInlineReply: Boolean
)

enum class NotificationImportance(val value: Int){
    MIN(NotificationCompat.PRIORITY_MIN),
    LOW(NotificationCompat.PRIORITY_LOW),
    DEFAULT(NotificationCompat.PRIORITY_DEFAULT),
    HIGH(NotificationCompat.PRIORITY_HIGH)
}

