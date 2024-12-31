package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.first.about.AboutMeScreen

fun NavGraphBuilder.firstGraph(navHostController: NavHostController){
    navigation<Screen.FirstGraph>(startDestination = Screen.FirstScreen){
        composable<Screen.FirstScreen> {
            FirstScreen(navHostController)
        }
        composable<Screen.AboutMeScreen> {
            AboutMeScreen(navHostController)
        }
    }
}