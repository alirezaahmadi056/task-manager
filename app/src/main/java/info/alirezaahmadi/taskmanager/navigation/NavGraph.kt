package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alirezaahmadi.taskmanager.ui.graph.first.about.AboutMeScreen
import info.alirezaahmadi.taskmanager.ui.graph.duties.dutiesGraph
import info.alirezaahmadi.taskmanager.ui.graph.first.FirstScreen
import info.alirezaahmadi.taskmanager.ui.graph.first.firstGraph
import info.alirezaahmadi.taskmanager.ui.graph.routine.routineGraph

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
        routineGraph(navHostController)
        dutiesGraph(navHostController)
    }

}