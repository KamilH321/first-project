package com.example.firstproject

import android.app.Application
import ru.itis.di.ServiceLocator

class FirstProjectApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(applicationContext)
    }
}