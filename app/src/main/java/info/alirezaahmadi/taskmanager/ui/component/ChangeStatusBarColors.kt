package info.alirezaahmadi.taskmanager.ui.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.viewModel.ThemViewModel


@Composable
fun ChangeStatusBarColors(
    navHostController: NavHostController,
    themViewModel: ThemViewModel
) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()
    val darkThem by themViewModel.darkThem.collectAsState()
    val currentGraph = backStackEntry.value?.destination?.parent?.route
    val defaultStatusBarColor = if (darkThem) Color.Black else Color.White
    val defaultNavBarColor = if (darkThem) Color.Black else Color.White

    LaunchedEffect(currentGraph, darkThem) {
        when (currentGraph?.substringAfterLast(".")) {
            Screen.DreamGraph::class.simpleName,
            Screen.GoalsGraph::class.simpleName -> {
                systemUiController.setSystemBarsColor(Color.White)
            }

            Screen.FirstGraph::class.simpleName,
            Screen.DutiesGraph::class.simpleName -> {
                systemUiController.setSystemBarsColor(defaultStatusBarColor)
            }

            Screen.WeeklyRoutineGraph::class.simpleName,
            Screen.ExerciseProgramGraph::class.simpleName -> {
                systemUiController.setStatusBarColor(
                    if (darkThem) Color(0xff3B3B3B) else Color(0xffECECEC)
                )
                systemUiController.setNavigationBarColor(defaultNavBarColor)
            }

            Screen.SkinRoutineGraph::class.simpleName -> {
                systemUiController.setStatusBarColor(Color(0xffFFEDD8))
                systemUiController.setNavigationBarColor(Color.White)
            }

            Screen.MedicineGraph::class.simpleName -> {
                systemUiController.setStatusBarColor(Color(0xffC3D8C7))
                systemUiController.setNavigationBarColor(Color.White)
            }

            else -> {
                systemUiController.setSystemBarsColor(defaultStatusBarColor)
            }
        }
    }
}

