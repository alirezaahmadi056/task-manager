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
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.ui.graph.MainScreen
import info.alirezaahmadi.taskmanager.ui.graph.SearchScreen
import info.alirezaahmadi.taskmanager.ui.graph.about.AboutMeScreen
import info.alirezaahmadi.taskmanager.ui.graph.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.graph.notes.addNotes.AddNotesScreen
import info.alirezaahmadi.taskmanager.ui.graph.task.TaskScreen
import info.alirezaahmadi.taskmanager.ui.graph.task.addTask.AddTaskScreen

@Composable
fun NavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    darkThem: (Boolean) -> Unit,
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.MainScreen,
    ) {
        composable<Screen.MainScreen> {
            MainScreen(navHostController = navHostController, darkTheme = darkThem)
        }
        composable<Screen.NotesScreen> {
            NotesScreen(navHostController)
        }
        composable<Screen.TaskScreen> {
            TaskScreen(navHostController)
        }
        composable<Screen.AddNotesScreen>(
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
            val args =it.toRoute<Screen.AddNotesScreen>()
            AddNotesScreen(
                navHostController = navHostController,
                id = args.id ?:0
            )
        }

        composable<Screen.AddTaskScreen>(
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
            val args =it.toRoute<Screen.AddTaskScreen>()
            AddTaskScreen(
                navHostController = navHostController,
                id = args.id?:0,
                lastId = args.lastId?: 10
            )
        }
        composable<Screen.AboutMeScreen> {
            AboutMeScreen(navHostController)
        }
        composable<Screen.SearchScreen> {
            SearchScreen(navHostController)
        }
    }

}