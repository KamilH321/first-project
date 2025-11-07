package com.example.firstproject.navScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firstproject.navigation.BottomNavigationBar
import com.example.firstproject.navigation.Tab
import com.example.firstproject.notifications.NotificationHandler

@Composable
fun MainScreen(notificationHandler: NotificationHandler) {
    val navController = rememberNavController()
    val items = listOf(Tab.Settings, Tab.Edit, Tab.Messages)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, items)
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Tab.Settings.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Tab.Settings.route) {
                NotificationSettingsScreen(notificationHandler = notificationHandler)
            }
            composable(Tab.Edit.route) {
                EditNotificationScreen(notificationHandler = notificationHandler)
            }
            composable(Tab.Messages.route) {
                MessagesScreen()
            }
        }
    }
}