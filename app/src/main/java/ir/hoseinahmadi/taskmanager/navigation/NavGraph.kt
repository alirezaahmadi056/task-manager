package ir.hoseinahmadi.taskmanager.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.hoseinahmadi.taskmanager.ui.screen.SearchScreen
import ir.hoseinahmadi.taskmanager.ui.screen.about.AboutMeScreen
import ir.hoseinahmadi.taskmanager.ui.screen.notes.NotesScreen
import ir.hoseinahmadi.taskmanager.ui.screen.notes.addNotes.AddNotesScreen
import ir.hoseinahmadi.taskmanager.ui.screen.task.TaskScreen
import ir.hoseinahmadi.taskmanager.ui.screen.task.addTask.AddTaskScreen

@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.NotesScreen.route,
    ) {
        composable(Screen.NotesScreen.route) {
            NotesScreen(navHostController)
        }
        composable(Screen.TaskScreen.route) {
            TaskScreen(navHostController)
        }
        composable(Screen.AddNotesScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(800)
                )
            },
            exitTransition = { fadeOut(animationSpec = tween(700)) },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(800)
                )
            },
            popExitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            AddNotesScreen(
                navHostController = navHostController,
                id = it.arguments?.getInt("id") ?: 0,
            )
        }

        composable(Screen.AddTaskScreen.route +"?id={id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    defaultValue =0
                }
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(800)
                )
            },
            exitTransition = { fadeOut(animationSpec = tween(700)) },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(800)
                )
            },
            popExitTransition = { fadeOut(animationSpec = tween(700)) }
            ) {
            AddTaskScreen(navHostController = navHostController,
                id =it.arguments?.getInt("id")?:0
            )
        }
        composable(Screen.AboutMeScreen.route){
            AboutMeScreen(navHostController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navHostController)
        }
    }

}