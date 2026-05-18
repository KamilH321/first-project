package ru.itis.analytics.api

interface Analytics {

    fun trackEvent(eventName: String, vararg params: Pair<String, Any>)

    fun trackScreen(screenName: String)
}