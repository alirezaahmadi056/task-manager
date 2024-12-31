package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import info.alirezaahmadi.taskmanager.ui.graph.duties.dutiesGraph
import info.alirezaahmadi.taskmanager.ui.graph.first.firstGraph
import info.alirezaahmadi.taskmanager.ui.graph.routineWeekly.weeklyRoutineGraph

@Composable
fun NavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    darkThem: (Boolean) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.FirstGraph,
    ) {
        firstGraph(navHostController)
        weeklyRoutineGraph(navHostController)
        dutiesGraph(navHostController)
    }

}