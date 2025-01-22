package info.alirezaahmadi.taskmanager.ui.graph.goals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen.GoalsFullScreen
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsScreen
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel

fun NavGraphBuilder.goalsGraph(
    navHostController: NavHostController,
    goalsViewModel: GoalsViewModel
) {
    navigation<Screen.GoalsGraph>(startDestination = Screen.GoalsScreen) {
        composable<Screen.GoalsScreen> {
            GoalsScreen(navHostController, goalsViewModel)
        }
        composable<Screen.GoalsFullScreen> {
            val args = it.toRoute<Screen.GoalsFullScreen>()
            GoalsFullScreen(
                navHostController = navHostController,
                pageIndex = args.pageIndex,
                goalsViewModel = goalsViewModel
            )
        }
    }
}