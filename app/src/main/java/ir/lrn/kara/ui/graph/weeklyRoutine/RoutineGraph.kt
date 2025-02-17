package ir.lrn.kara.ui.graph.weeklyRoutine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.lrn.kara.navigation.Screen

fun NavGraphBuilder.weeklyRoutineGraph(navHostController: NavHostController) {
    navigation<Screen.WeeklyRoutineGraph>(startDestination =Screen.WeeklyRoutineScreen ){
        composable<Screen.WeeklyRoutineScreen> { WeeklyRoutineScreen(navHostController) }
    }
}