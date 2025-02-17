package ir.lrn.kara.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.lrn.kara.ui.graph.curriculum.curriculumGraph
import ir.lrn.kara.ui.graph.dreame.dreamGraph
import ir.lrn.kara.ui.graph.duties.dutiesGraph
import ir.lrn.kara.ui.graph.exerciseProgram.exerciseProgramGraph
import ir.lrn.kara.ui.graph.first.firstGraph
import ir.lrn.kara.ui.graph.goals.goalsGraph
import ir.lrn.kara.ui.graph.medicine.medicineGraph
import ir.lrn.kara.ui.graph.skinRoutine.skinRoutineGraph
import ir.lrn.kara.ui.graph.weeklyRoutine.weeklyRoutineGraph
import ir.lrn.kara.viewModel.CurriculumViewModel
import ir.lrn.kara.viewModel.DreamViewModel
import ir.lrn.kara.viewModel.ExerciseProgramViewModel
import ir.lrn.kara.viewModel.GoalsViewModel
import ir.lrn.kara.viewModel.MedicineViewModel
import ir.lrn.kara.viewModel.ThemViewModel

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