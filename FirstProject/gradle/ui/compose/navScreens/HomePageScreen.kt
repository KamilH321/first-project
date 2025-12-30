package com.example.firstproject.ui.compose.navScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.firstproject.db.entity.FilmEntity
import com.example.firstproject.navigation.AddContentScreenObject

enum class SortType { TITLE, YEAR }

@Composable
fun HomeScreen(
    navController: NavController,
    sessionManager: SessionManager,
    coroutineScope: CoroutineScope
) {
    val userId = sessionManager.getUserId()
    val dao = DatabaseService.getDatabase().filmDao

    var films by remember { mutableStateOf<List<FilmEntity>>(emptyList()) }
    var sort by remember { mutableStateOf(SortType.TITLE) }
    var showSheet by remember { mutableStateOf(false) }

//    LaunchedEffect(sort) {
//        films = dao.getFilmsDataByUserId(userId).sortedBy {
//            if (sort == SortType.TITLE) it.title else it.releaseYear
//        }
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton {
//                navController.navigate(AddContentScreenObject)
//            }
//        }
//    ) {
//        Column {
//            Button(onClick = { showSheet = true }) {
//                Text("Sort")
//            }
//
//            LazyColumn {
//                items(films) {
//                    Text("${it.title} (${it.releaseYear})")
//                }
//            }
//        }
//    }
//
//    if (showSheet) {
//        ModalBottomSheet(onDismissRequest = { showSheet = false }) {
//            Button({ sort = SortType.TITLE; showSheet = false }) { Text("By title") }
//            Button({ sort = SortType.YEAR; showSheet = false }) { Text("By year") }
//        }
//    }
}
