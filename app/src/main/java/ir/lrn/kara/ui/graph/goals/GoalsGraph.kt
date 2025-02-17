package ir.lrn.kara.ui.graph.goals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.goals.addGoals.AddGoalsScreen
import ir.lrn.kara.ui.graph.goals.detail.GoalsDetailScreen
import ir.lrn.kara.ui.graph.goals.fullScreen.GoalsFullScreen
import ir.lrn.kara.ui.graph.goals.main.GoalsScreen
import ir.lrn.kara.ui.graph.goals.main.MovieBookDetailScreen
import ir.lrn.kara.viewModel.GoalsViewModel

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
        composable<Screen.AddGoalsScreen> {
            val args = it.toRoute<Screen.AddGoalsScreen>()
            AddGoalsScreen(
                navHostController = navHostController,
                id = args.id,
                time = args.timeFrame,
                goalsViewModel = goalsViewModel
            )
        }
        composable<Screen.GoalsDetail> {
            val args = it.toRoute<Screen.GoalsDetail>()
            GoalsDetailScreen(
                navHostController = navHostController,
                id = args.id,
                goalsViewModel = goalsViewModel
            )
        }
        composable<Screen.MovieBookDetailScreen> {
            val args = it.toRoute<Screen.MovieBookDetailScreen>()
            MovieBookDetailScreen(
                navHostController = navHostController,
                id = args.id,
                type = args.type
            )
        }

    }
}