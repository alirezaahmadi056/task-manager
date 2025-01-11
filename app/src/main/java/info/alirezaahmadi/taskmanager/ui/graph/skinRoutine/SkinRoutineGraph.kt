package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine.AddSkinRoutineScreen

fun NavGraphBuilder.skinRoutineGraph(navHostController: NavHostController){
    navigation<Screen.SkinRoutineGraph>(startDestination = Screen.SkinRoutineScreen){
        composable<Screen.SkinRoutineScreen> {
            SkinRoutineScreen(navHostController)
        }
        composable<Screen.AddSkinRoutineScreen> {
            AddSkinRoutineScreen(navHostController)
        }

    }
}