package com.example.firstproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.firstproject.di.AppComponent
import com.example.firstproject.di.DaggerAppComponent
import java.util.UUID
import androidx.core.content.edit
import androidx.core.content.getSystemService
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirstProjectApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        setUpCrashlyticsUser()
        createNotificationChannels()


    }

    private fun setUpCrashlyticsUser() {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        var userUUID = prefs.getString("user_uuid", null)

        if (userUUID == null) {
            userUUID = UUID.randomUUID().toString()
            prefs.edit { putString("user_uuid", userUUID) }
        }

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setUserId(userUUID)
        crashlytics.setCustomKey("user_internal_id", userUUID)
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            val authChannel = NotificationChannel(
                "auth_chanel",
                "Авторизация и безопасность",
                NotificationManager.IMPORTANCE_HIGH
            )

            val promoChanel = NotificationChannel(
                "promo_channel",
                "Промо-акции и скидки",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager?.createNotificationChannels(listOf(authChannel, promoChanel))
        }
    }
}

fun Context.appComponent(): AppComponent {
    return when(this) {
        is FirstProjectApp -> appComponent
        else -> applicationContext.appComponent()
    }
}