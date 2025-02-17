package ir.lrn.kara.ui.graph.first

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.first.about.AboutMeScreen
import ir.lrn.kara.viewModel.ThemViewModel

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