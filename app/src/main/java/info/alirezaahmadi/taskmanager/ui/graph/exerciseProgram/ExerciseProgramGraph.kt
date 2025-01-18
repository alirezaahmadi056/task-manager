package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.addExercise.AddExerciseProgramScreen
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.completedExercise.CompletedExerciseScreen
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise.StartExerciseProgramScreen
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel

fun NavGraphBuilder.exerciseProgramGraph(
    navHostController: NavHostController,
    exerciseProgramViewModel: ExerciseProgramViewModel,
) {
    navigation<Screen.ExerciseProgramGraph>(
        startDestination = Screen.ExerciseProgramScreen
    ) {
        composable<Screen.ExerciseProgramScreen> {
            ExerciseProgramScreen(navHostController = navHostController,exerciseProgramViewModel)
        }
        composable<Screen.AddExerciseProgramScreen> {
            val args = it.toRoute<Screen.AddExerciseProgramScreen>()
            AddExerciseProgramScreen(navHostController = navHostController, id = args.id,exerciseProgramViewModel)
        }
        composable<Screen.StartExerciseProgramScreen> {
            val args = it.toRoute<Screen.StartExerciseProgramScreen>()
            StartExerciseProgramScreen(navHostController = navHostController, day = args.day,exerciseProgramViewModel)
        }
        composable<Screen.CompletedExerciseScreen> {
            val args =it.toRoute<Screen.CompletedExerciseScreen>()
            CompletedExerciseScreen(
                navHostController = navHostController,
                dayName = args.dayName,
                exerciseList = args.exerciseList,
                time = args.time
            )
        }

    }


}