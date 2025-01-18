package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
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
    val allExercise by exerciseProgramViewModel.allExerciseProgram.collectAsState()
    val enableStartExercise = remember(key1 = pagerState.currentPage, key2 = allExercise) {
        derivedStateOf {
            val filteredExercises = allExercise.filter { exercise ->
                exercise.dayWeek.contains(dayWeek.getOrNull(pagerState.currentPage))
            }
            filteredExercises.isNotEmpty()
        }
    }

    var singleId by remember { mutableStateOf<Int?>(null) }

    DialogDeleteItemTask(
        title = "حذف حرکت ",
        body = "از حذف کردن این جرکت ورزشی اطمینان دارید؟",
        show = singleId != null,
        onBack = { singleId = null },
        onDeleteItem = {
            singleId?.let { exerciseProgramViewModel.deletedExerciseProgram(it) }
            singleId = null
        }
    )
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    enabled = enableStartExercise.value,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(0.9f),
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 8.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff9747FF),
                        contentColor = Color.White
                    ),
                    onClick = {
                        navHostController.navigate(Screen.StartExerciseProgramScreen(dayWeek[pagerState.currentPage])) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.start_exercise),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
            val currentExerciseProgramItems =
                remember(key1 = allExercise, key2 = page) {
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
                            navHostController.navigate(Screen.AddExerciseProgramScreen(id = exercise.id)) {
                                launchSingleTop = true
                            }
                        },
                        onLongClick = {
                            singleId =exercise.id
                        }
                    )
                }

            }
        }
    }
}

