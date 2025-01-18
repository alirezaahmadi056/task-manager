package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel
import kotlinx.coroutines.launch

@Composable
fun StartExerciseProgramScreen(
    navHostController: NavHostController,
    day: String,
    exerciseProgramViewModel: ExerciseProgramViewModel = hiltViewModel()
) {
    var startTime by rememberSaveable { mutableLongStateOf(0L) }
    LaunchedEffect(Unit) { }
    val allExercise by exerciseProgramViewModel.allExerciseProgram.collectAsState()
    val currentDayExercise =
        remember(key1 = allExercise) { allExercise.filter { it.dayWeek.contains(day) } }
    val pagerState = rememberPagerState { currentDayExercise.size }
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            SingleExerciseBottomBar(
                maxSize = currentDayExercise.size,
                currentIndex = pagerState.currentPage,
                enableScroll = !pagerState.isScrollInProgress,
                onFinish = {},
                onPrevious = {
                    scope.launch {
                        pagerState.animateScrollToPage(it, animationSpec = tween(500))
                    }
                },
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(it, animationSpec = tween(500))
                    }
                }
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
                dayName = day,
                exerciseList =currentDayExercise ,
                currentExercise = currentExercise,
                onBack = {navHostController.navigateUp()},
                onScrollPage = {pageIndex->
                    if (pageIndex!=page){
                        scope.launch {
                            pagerState.animateScrollToPage(pageIndex, animationSpec = tween(600))
                        }
                    }
                }
            )
        }
    }

}