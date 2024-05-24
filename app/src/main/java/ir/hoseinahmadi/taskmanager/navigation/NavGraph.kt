package ir.hoseinahmadi.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hoseinahmadi.taskmanager.ui.screen.addNotes.AddNotesScreen
import ir.hoseinahmadi.taskmanager.ui.screen.notes.NotesScreen
import ir.hoseinahmadi.taskmanager.ui.screen.task.TaskScreen

@Composable
fun NavGraph(navHostController: NavHostController){

    NavHost(navController = navHostController,
        startDestination = Screen.NotesScreen.route) {
        composable(Screen.NotesScreen.route){
            NotesScreen(navHostController)
        }
        composable(Screen.TaskScreen.route){
            TaskScreen()
        }
        composable(Screen.AddNotesScreen.route){
            AddNotesScreen(navHostController = navHostController)
        }

    }

}