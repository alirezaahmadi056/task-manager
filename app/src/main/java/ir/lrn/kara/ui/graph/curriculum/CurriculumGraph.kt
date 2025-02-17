package ir.lrn.kara.ui.graph.curriculum

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.curriculum.add.AddCurriculumScreen
import ir.lrn.kara.ui.graph.curriculum.main.CurriculumScreen
import ir.lrn.kara.viewModel.CurriculumViewModel

fun NavGraphBuilder.curriculumGraph(
    navHostController: NavHostController,
    curriculumViewModel: CurriculumViewModel
) {

    navigation<Screen.CurriculumGraph>(startDestination = Screen.CurriculumScreen) {
        composable<Screen.CurriculumScreen> {
            CurriculumScreen(navHostController, curriculumViewModel)
        }
        composable<Screen.AddCurriculumScreen> {
            val args = it.toRoute<Screen.AddCurriculumScreen>()
            AddCurriculumScreen(
                navHostController = navHostController,
                id = args.id,
                day = args.day,
                curriculumViewModel = curriculumViewModel
            )
        }
    }
}