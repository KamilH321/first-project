package com.example.firstproject.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController, items: List<Tab>) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val current = navBackStackEntry.value?.destination?.route ?: Tab.Settings.route

    NavigationBar {
        items.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = stringResource(tab.titleRes)) },
                label = { Text(stringResource(tab.titleRes)) },
                selected = current == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}