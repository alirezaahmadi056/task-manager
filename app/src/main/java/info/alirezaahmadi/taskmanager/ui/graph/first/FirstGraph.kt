package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.first.about.AboutMeScreen
import info.alirezaahmadi.taskmanager.viewModel.ThemViewModel

fun NavGraphBuilder.firstGraph(navHostController: NavHostController, themViewModel: ThemViewModel) {
    navigation<Screen.FirstGraph>(startDestination = Screen.FirstScreen) {
        composable<Screen.FirstScreen> {
            FirstScreen(navHostController,themViewModel)
        }
        composable<Screen.AboutMeScreen> {
            AboutMeScreen(navHostController)
        }
    }
}