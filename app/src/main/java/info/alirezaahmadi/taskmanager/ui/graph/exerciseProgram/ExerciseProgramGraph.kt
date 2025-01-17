package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.addExercise.AddExerciseProgramScreen

fun NavGraphBuilder.exerciseProgramGraph(
    navHostController: NavHostController,
) {
    navigation<Screen.ExerciseProgramGraph>(
        startDestination = Screen.ExerciseProgramScreen
    ) {
        composable<Screen.ExerciseProgramScreen> {
            ExerciseProgramScreen(navHostController = navHostController)
        }
        composable<Screen.AddExerciseProgramScreen> {
            val args =it.toRoute<Screen.AddExerciseProgramScreen>()
            AddExerciseProgramScreen(navHostController = navHostController,id=args.id)
        }
    }


}