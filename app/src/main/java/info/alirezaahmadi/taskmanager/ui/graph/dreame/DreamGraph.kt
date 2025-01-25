package info.alirezaahmadi.taskmanager.ui.graph.dreame

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.dreame.addDream.AddDreamScreen
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel

fun NavGraphBuilder.dreamGraph(
    navHostController: NavHostController,
    dreamViewModel: DreamViewModel,
) {

    navigation<Screen.DreamGraph>(startDestination = Screen.DreamScreen) {
        composable<Screen.DreamScreen> {
            DreamScreen(navHostController = navHostController, dreamViewModel = dreamViewModel)
        }
        composable<Screen.AddDreamsScreen> {
            val args = it.toRoute<Screen.AddDreamsScreen>()
            AddDreamScreen(
                navHostController = navHostController,
                id = args.id,
                dreamViewModel = dreamViewModel
            )
        }
    }
}