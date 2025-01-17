package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel

@Composable
fun ExerciseProgramScreen(
    navHostController: NavHostController,
    exerciseProgramViewModel: ExerciseProgramViewModel = hiltViewModel()
){
    Text("ExerciseProgramScreen")
}