package info.alirezaahmadi.taskmanager.ui.graph.weeklyRoutine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen

fun NavGraphBuilder.weeklyRoutineGraph(navHostController: NavHostController) {
    navigation<Screen.WeeklyRoutineGraph>(startDestination =Screen.WeeklyRoutineScreen ){
        composable<Screen.WeeklyRoutineScreen> { WeeklyRoutineScreen(navHostController) }
    }
}