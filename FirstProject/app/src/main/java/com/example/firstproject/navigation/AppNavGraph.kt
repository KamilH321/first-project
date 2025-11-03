package com.example.firstproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.firstproject.models.NoteDataModel
import com.example.firstproject.navScreens.addnotepage.AddNoteScreen
import com.example.firstproject.navScreens.loginpage.LoginScreen
import com.example.firstproject.navScreens.notespage.NotesScreen

@Composable
fun AppNavGraph(navController: NavHostController, notes: SnapshotStateList<NoteDataModel>){
    NavHost(
        navController = navController,
        startDestination = LoginPageDataObject
    ) {
        composable<LoginPageDataObject> {
            LoginScreen(navController)
        }

        composable<NotesPageDataObject> { entry ->
            val args = entry.toRoute<NotesPageDataObject>()

            NotesScreen(
                navController,
                args.email,
                notes)
        }

        composable<AddNotePageDataObject> {
            AddNoteScreen(
                navController,
                onNoteSave = {note -> notes.add(note)}
            )
        }
    }
}