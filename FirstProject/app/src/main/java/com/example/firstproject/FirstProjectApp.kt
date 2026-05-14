package com.example.firstproject

import android.app.Application
import android.content.Context
import com.example.firstproject.di.AppComponent
import com.example.firstproject.di.DaggerAppComponent

class FirstProjectApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

    }
}

fun Context.appComponent(): AppComponent {
    return when(this) {
        is FirstProjectApp -> appComponent
        else -> applicationContext.appComponent()
    }
}