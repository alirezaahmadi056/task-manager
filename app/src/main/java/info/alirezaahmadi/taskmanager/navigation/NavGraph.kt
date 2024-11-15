package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import info.alirezaahmadi.taskmanager.ui.screen.MainScreen
import info.alirezaahmadi.taskmanager.ui.screen.SearchScreen
import info.alirezaahmadi.taskmanager.ui.screen.about.AboutMeScreen
import info.alirezaahmadi.taskmanager.ui.screen.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes.AddNotesScreen
import info.alirezaahmadi.taskmanager.ui.screen.task.TaskScreen
import info.alirezaahmadi.taskmanager.ui.screen.task.addTask.AddTaskScreen

@Composable
fun NavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    darkThem: (Boolean) -> Unit,
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.MainScreen.route,
    ) {

        composable(Screen.MainScreen.route) {
            MainScreen(navHostController = navHostController, darkTheme = darkThem)
        }
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

        composable(Screen.AddTaskScreen.route + "?id={id}&lastId={lastId}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("lastId") {
                    type = NavType.IntType
                    defaultValue = 0
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
            AddTaskScreen(
                navHostController = navHostController,
                id = it.arguments?.getInt("id") ?: 0,
                lastId = it.arguments?.getInt("lastId") ?: 10
            )
        }
        composable(Screen.AboutMeScreen.route) {
            AboutMeScreen(navHostController)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(navHostController)
        }
    }

}