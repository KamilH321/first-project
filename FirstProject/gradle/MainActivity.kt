package com.example.firstproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.navigation.AppNavGraph
import com.example.firstproject.navigation.HomeScreenObject
import com.example.firstproject.ui.theme.FirstProjectTheme
import com.example.firstproject.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        DatabaseService.initDatabase(applicationContext)

        val dbPath = applicationContext.getDatabasePath("films_catalog.db")
        Log.d("DB_DEBUG", "Database path: ${dbPath.absolutePath}")
        Log.d("DB_DEBUG", "Database exists: ${dbPath.exists()}")

        setContent {
            FirstProjectTheme {

                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(applicationContext) }
                val coroutineScope = rememberCoroutineScope()

                if (sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(HomeScreenObject)
                    }
                }

                AppNavGraph(
                    navController = navController,
                    database = DatabaseService.getDatabase(),
                    sessionManager = sessionManager,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

