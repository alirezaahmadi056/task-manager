package info.alirezaahmadi.taskmanager.ui.graph.routine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen

fun NavGraphBuilder.routineGraph(navHostController: NavHostController) {
    navigation<Screen.RoutineGraph>(startDestination =Screen.RoutineScreen ){
        composable<Screen.RoutineScreen> { RoutineScreen(navHostController) }
    }
}