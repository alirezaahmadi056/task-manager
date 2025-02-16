package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine.AddSkinRoutineScreen

fun NavGraphBuilder.skinRoutineGraph(navHostController: NavHostController) {
    navigation<Screen.SkinRoutineGraph>(startDestination = Screen.SkinRoutineScreen) {
        composable<Screen.SkinRoutineScreen> {
            SkinRoutineScreen(navHostController)
        }
        composable<Screen.AddSkinRoutineScreen> {
            val args = it.toRoute<Screen.AddSkinRoutineScreen>()
            AddSkinRoutineScreen(
                navHostController = navHostController,
                id = args.id,
                day = args.day,
                time = args.time
            )
        }

    }
}