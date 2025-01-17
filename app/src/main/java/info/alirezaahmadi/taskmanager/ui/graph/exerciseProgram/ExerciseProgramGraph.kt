package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen

fun NavGraphBuilder.exerciseProgramGraph(
    navHostController: NavHostController,
) {
    navigation<Screen.ExerciseProgramGraph>(
        startDestination = Screen.ExerciseProgramScreen
    ) {
        composable<Screen.ExerciseProgramScreen> {
            ExerciseProgramScreen(navHostController = navHostController)
        }
    }


}