package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import info.alirezaahmadi.taskmanager.ui.graph.curriculum.curriculumGraph
import info.alirezaahmadi.taskmanager.ui.graph.dreame.dreamGraph
import info.alirezaahmadi.taskmanager.ui.graph.duties.dutiesGraph
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.exerciseProgramGraph
import info.alirezaahmadi.taskmanager.ui.graph.first.firstGraph
import info.alirezaahmadi.taskmanager.ui.graph.goals.goalsGraph
import info.alirezaahmadi.taskmanager.ui.graph.medicine.medicineGraph
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.skinRoutineGraph
import info.alirezaahmadi.taskmanager.ui.graph.weeklyRoutine.weeklyRoutineGraph
import info.alirezaahmadi.taskmanager.viewModel.CurriculumViewModel
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel
import info.alirezaahmadi.taskmanager.viewModel.ThemViewModel

@Composable
fun NavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    themViewModel: ThemViewModel,
    exerciseProgramViewModel: ExerciseProgramViewModel= hiltViewModel(),
    goalsViewModel: GoalsViewModel= hiltViewModel(),
    medicineViewModel: MedicineViewModel= hiltViewModel(),
    dreamViewModel: DreamViewModel= hiltViewModel(),
    curriculumViewModel: CurriculumViewModel= hiltViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.FirstGraph,
    ) {
        firstGraph(navHostController,themViewModel)
        weeklyRoutineGraph(navHostController)
        dutiesGraph(navHostController)
        skinRoutineGraph(navHostController)
        exerciseProgramGraph(navHostController,exerciseProgramViewModel)
        goalsGraph(navHostController,goalsViewModel)
        medicineGraph(navHostController, medicineViewModel)
        dreamGraph(navHostController,dreamViewModel)
        curriculumGraph(navHostController,curriculumViewModel)
    }

}