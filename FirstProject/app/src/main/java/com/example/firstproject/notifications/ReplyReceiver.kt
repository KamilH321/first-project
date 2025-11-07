package com.example.firstproject.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.example.firstproject.Keys
import com.example.firstproject.models.Message
import com.example.firstproject.repository.MessageRepository

class ReplyReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val reply = RemoteInput.getResultsFromIntent(intent)
            ?.getCharSequence(Keys.REMOTE_INPUT_KEY)
            ?.toString()

        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)

        if(!reply.isNullOrBlank()){
            MessageRepository.addMessage(
                Message(
                    text = reply,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        if (notificationId != -1){
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }

    companion object {
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
        const val ACTION = "REPLY_ACTION"
    }

}