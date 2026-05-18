package ru.itis.analytics.impl

import android.content.Context
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import ru.itis.analytics.api.Analytics
import ru.itis.utils.context.ApplicationContext
import javax.inject.Inject

class AnalyticsImpl @Inject constructor(
    @ApplicationContext private val context: Context
): Analytics {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun trackEvent(
        eventName: String,
        vararg params: Pair<String, Any>
    ) {
        val bundle = bundleOf(*params)
        firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun trackScreen(screenName: String) {
        trackEvent(EVENT_NAME, PARAMS to screenName)
    }

    companion object {
        private const val EVENT_NAME = "screen_view"
        private const val PARAMS = "screen_name"
    }
}