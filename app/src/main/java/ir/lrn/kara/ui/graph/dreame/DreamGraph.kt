package ir.lrn.kara.ui.graph.dreame

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.dreame.addDream.AddDreamScreen
import ir.lrn.kara.ui.graph.dreame.dreamDetail.DreamDetailScreen
import ir.lrn.kara.viewModel.DreamViewModel

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
        composable<Screen.DreamDetailScreen> {
            val args =it.toRoute<Screen.DreamDetailScreen>()
            DreamDetailScreen(
                navHostController, args.id, dreamViewModel
            )
        }
    }
}