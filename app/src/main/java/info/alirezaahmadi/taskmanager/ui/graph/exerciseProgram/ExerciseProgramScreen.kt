package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.SkinTopBar
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.persianDayOfWeek
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseProgramScreen(
    navHostController: NavHostController,
    exerciseProgramViewModel: ExerciseProgramViewModel = hiltViewModel()
) {
    val day = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    val dayWeek = remember { Constants.deyWeek }
    val pagerState = rememberPagerState(initialPage = persianDayOfWeek[day] ?: 0) { dayWeek.size }
    val coroutineScope = rememberCoroutineScope()
    val allExercise by exerciseProgramViewModel.getAllExerciseProgram().collectAsState(emptyList())

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ExerciseTopBar(
                allDayWeek = dayWeek,
                currentPage = pagerState.currentPage,
                onSelected = { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page, animationSpec = tween(600))
                    }
                },
                onBack = { navHostController.navigateUp() }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        bottomBar = {

        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(MaterialTheme.colorScheme.background)
        ) { page ->
            val currentExerciseProgramItems = remember(key1 = allExercise, key2 = pagerState.currentPage) {
                allExercise.filter { it.dayWeek.contains(dayWeek[page]) }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    SectionAddExercise(
                        day = dayWeek[page],
                        onAddClick = { navHostController.navigate(Screen.AddExerciseProgramScreen()) }
                    )
                }
                items(items = currentExerciseProgramItems, key = { it.id }) { exercise ->
                    ExerciseItemCard(
                        item = exercise,
                        onClick = {
                            navHostController.navigate(Screen.AddExerciseProgramScreen(id = exercise.id)){
                                launchSingleTop =true
                            }
                        },
                        onLongClick = {}
                    )
                }

            }
        }
    }
}

