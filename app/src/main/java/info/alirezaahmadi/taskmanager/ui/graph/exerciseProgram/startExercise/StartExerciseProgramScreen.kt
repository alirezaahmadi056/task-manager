package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel

@Composable
fun StartExerciseProgramScreen(
    navHostController: NavHostController,
    day: String,
    exerciseProgramViewModel: ExerciseProgramViewModel = hiltViewModel()
) {
    val allExercise by exerciseProgramViewModel.allExerciseProgram.collectAsState()
    val currentDayExercise =
        remember(key1 = allExercise) { allExercise.filter { it.dayWeek.contains(day) } }
    val pagerState = rememberPagerState { currentDayExercise.size }

    Scaffold(
        bottomBar = {
            SingleExerciseBottomBar(
                maxSize = currentDayExercise.size,
                currentIndex = pagerState.currentPage
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            val currentExercise =
                remember(key1 = currentDayExercise, key2 = page) { currentDayExercise.getOrNull(page) }
            SingleExerciseComponent(
                index = page,
                currentExercise = currentExercise,
                onBack = {navHostController.navigateUp()}
            )
        }
    }

}