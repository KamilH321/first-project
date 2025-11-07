package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.firstproject.navScreens.MainScreen
import com.example.firstproject.notifications.NotificationHandler
import com.example.firstproject.ui.theme.FirstProjectTheme
import com.example.firstproject.utils.ResManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                123
            )
        }

        enableEdgeToEdge()
        setContent {
            FirstProjectTheme {
                val context = LocalContext.current
                val notificationHandler = remember {
                    NotificationHandler(context, ResManager(context))
                }

                MainScreen(notificationHandler)
            }
        }
    }
}
