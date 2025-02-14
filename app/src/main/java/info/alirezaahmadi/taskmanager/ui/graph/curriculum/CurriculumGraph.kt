package info.alirezaahmadi.taskmanager.ui.graph.curriculum

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.curriculum.add.AddCurriculumScreen
import info.alirezaahmadi.taskmanager.ui.graph.curriculum.main.CurriculumScreen
import info.alirezaahmadi.taskmanager.viewModel.CurriculumViewModel

fun NavGraphBuilder.curriculumGraph(
    navHostController: NavHostController,
    curriculumViewModel: CurriculumViewModel
) {

    navigation<Screen.CurriculumGraph>(startDestination = Screen.CurriculumScreen) {
        composable<Screen.CurriculumScreen> {
            CurriculumScreen(navHostController,curriculumViewModel)
        }
        composable<Screen.AddCurriculumScreen> {
            val args =it.toRoute<Screen.AddCurriculumScreen>()
            AddCurriculumScreen(navHostController, args.id, curriculumViewModel)
        }
    }
}