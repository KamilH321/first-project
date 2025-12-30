package com.example.firstproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstproject.db.AppDatabase
import com.example.firstproject.db.entity.UserEntity
import com.example.firstproject.ui.compose.navScreens.LoginScreen
import com.example.firstproject.ui.compose.navScreens.RegisterScreen
import com.example.firstproject.utils.SessionManager
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavGraph(
    navController: NavHostController,
    database: AppDatabase,
    sessionManager: SessionManager,
    coroutineScope: CoroutineScope,
){
    NavHost(
        navController = navController,
        startDestination = LoginScreenObject
    ){
        composable<LoginScreenObject> {
            LoginScreen(
                navController = navController,
                sessionManager = sessionManager,
                coroutineScope = coroutineScope
            )
        }

        composable<RegisterScreenObject> {
            RegisterScreen(
                navController = navController,
                coroutineScope = coroutineScope
            )
        }

        composable<HomeScreenObject> {
        }

        composable<AddContentScreenObject> {
        }

        composable<ProfileScreenObject> {
        }
    }
}