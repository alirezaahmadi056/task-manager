package ir.lrn.kara.ui.graph.duties

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.duties.notes.addNotes.AddNotesScreen
import ir.lrn.kara.ui.graph.duties.task.addTask.AddTaskScreen

fun NavGraphBuilder.dutiesGraph(navHostController: NavHostController){
    navigation<Screen.DutiesGraph>(startDestination = Screen.DutiesScreen){
        composable<Screen.DutiesScreen> {
            DutiesScreen(navHostController = navHostController, darkTheme = {})
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
        composable<Screen.SearchScreen> {
            SearchScreen(navHostController)
        }
    }
}