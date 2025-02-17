package ir.lrn.kara.ui.graph.exerciseProgram

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.DialogDeleteItemTask
import ir.lrn.kara.ui.component.SwipeToDismissBoxLayout
import ir.lrn.kara.util.Constants
import ir.lrn.kara.util.Constants.persianDayOfWeek
import ir.lrn.kara.viewModel.ExerciseProgramViewModel
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
        body = "از حذف کردن این حرکت ورزشی اطمینان دارید؟",
        show = singleId != null,
        onBack = { singleId = null },
        onDeleteItem = {
            singleId?.let { exerciseProgramViewModel.deletedExerciseProgram(it) }
            singleId = null
        }
    )
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ExerciseTopBar(
                allDayWeek = dayWeek,
                currentPage = pagerState.currentPage,
                onSelected = { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page, animationSpec = tween(550))
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
                        onAddClick = { navHostController.navigate(Screen.AddExerciseProgramScreen(day = dayWeek[page])) }
                    )
                }
                if (currentExerciseProgramItems.isEmpty()) {
                    stickyHeader { ExerciseEmpty() }
                }
                items(items = currentExerciseProgramItems, key = { it.id }) { exercise ->
                    SwipeToDismissBoxLayout(
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = true,
                        endToStart = { singleId = exercise.id },
                        startToEnd = {
                            navHostController.navigate(Screen.AddExerciseProgramScreen(id = exercise.id)) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        ExerciseItemCard(
                            item = exercise,
                            onClick = {
                                navHostController.navigate(
                                    Screen.AddExerciseProgramScreen(
                                        id = exercise.id
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            },
                            onLongClick = { singleId = exercise.id }
                        )
                    }

                }

            }


        }
    }
}

@Composable
private fun ExerciseEmpty() {
    val config = LocalConfiguration.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(config.screenHeightDp.dp / 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(15.dp))
        Icon(
            imageVector = Icons.Rounded.EmojiPeople,
            contentDescription = "",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "تمرینی برای این روز تنظیم نشده است!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground

        )
    }
}