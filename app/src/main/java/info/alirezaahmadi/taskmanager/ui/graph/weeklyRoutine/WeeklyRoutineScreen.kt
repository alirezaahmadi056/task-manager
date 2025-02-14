package info.alirezaahmadi.taskmanager.ui.graph.weeklyRoutine

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.EmptyList
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.ui.component.PageType
import info.alirezaahmadi.taskmanager.ui.component.SelectedSortNotList
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.persianDayOfWeek
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.WeeklyRoutineViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun WeeklyRoutineScreen(
    navHostController: NavHostController,
    weeklyRoutineViewModel: WeeklyRoutineViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val day = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    val dayWeek = remember { Constants.deyWeek }
    val pagerState = rememberPagerState(initialPage = persianDayOfWeek[day] ?: 0) { dayWeek.size }


    val allRoutine by weeklyRoutineViewModel.getAllRoutine().collectAsState(emptyList())
    var singleRoutine by remember { mutableStateOf<RoutineItem?>(null) }
    var showSheetAddRoutine by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    var sortOrder by remember { mutableIntStateOf(Constants.ROUTINE_SORT) }
    var showDialogDelete by remember { mutableStateOf(false) }
    val sortedNotesItem = remember(key1 = allRoutine, key2 = sortOrder) {
        when (sortOrder) {
            1 -> allRoutine.sortedBy { it.taskColor }
            2 -> allRoutine.sortedByDescending { it.taskColor == 2 }
            3 -> allRoutine.sortedByDescending { it.taskColor }
            4 -> allRoutine.sortedBy {
                val time = it.time
                val parts = time.split(":")
                if (parts.size == 2) {
                    val hours = parts[0].toIntOrNull() ?: 0
                    val minutes = parts[1].toIntOrNull() ?: 0
                    hours * 60 + minutes
                } else {
                    Int.MAX_VALUE
                }
            }

            else -> allRoutine.reversed()
        }
    }
    SelectedSortNotList(
        pageType = PageType.ROUTINE, noteSort = {}, taskSort = {},
        routineSort = { sorted ->
            sortOrder = sorted
        })


    DialogDeleteItemTask(
        title = "حذف روتین",
        body = "آیا از حذف این روتین اطمینان دارید؟",
        onBack = { showDialogDelete = false },
        onDeleteItem = {
            singleRoutine?.let { routine ->
                weeklyRoutineViewModel.deletedById(routine.id)
                alarmViewModel.cancelWeeklyAlarms(
                    context = context,
                    routineItem = routine
                )
                showDialogDelete = false
                scope.launch {
                    snackBarHostState.showSnackbar(
                        "روتین با موفقیت حذف شد",
                        duration = SnackbarDuration.Short,
                        withDismissAction = false
                    )
                }
            }
        },
        show = showDialogDelete && singleRoutine != null
    )

    SheetAddRoutine(
        show = showSheetAddRoutine,
        onDismissRequest = { showSheetAddRoutine = false },
        routineItem = singleRoutine,
        days = dayWeek,
        lastId = getOldId(allRoutine),
        routineViewModel = weeklyRoutineViewModel,
        alarmViewModel = alarmViewModel
    )
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                expanded = true,
                text = {
                    Text(
                        text = "روتین",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.NoteAdd,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                onClick = {
                    singleRoutine = null
                    showSheetAddRoutine = true
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        snackbarHost = {
            MySnackbarHost(snackBarHostState) { data ->
                IconButton(onClick = { data.dismiss() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.background,
                    )
                }
            }
        },
        topBar = {
            WeeklyRoutineTopBar(
                allTabs = dayWeek,
                pagerState = pagerState,
                onBack = {navHostController.navigateUp()}
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HorizontalPager(
            key = { "pageKey:${dayWeek[it]}" },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.Top,
            state = pagerState
        ) { page ->
            Routine(
                routines = filterRoutinesByDay(day = dayWeek[page], routines = sortedNotesItem),
                onClick = { data ->
                    singleRoutine = data
                    showSheetAddRoutine = true
                },
                onDeleted = { data ->
                    singleRoutine = data
                    showDialogDelete = true
                },
                currentDay = dayWeek[page]
            )
        }
    }
}

@Composable
private fun Routine(
    routines: List<RoutineItem>,
    onClick: (RoutineItem) -> Unit,
    onDeleted: (RoutineItem) -> Unit,
    currentDay: String
) {
    AnimatedContent(
        targetState = routines.isNotEmpty(), label = ""
    ) {notEmpty->
        if (notEmpty) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(Modifier.height(15.dp)) }
                items(items = routines, key = { it.id }) { routine ->
                    SwipeToDismissBoxLayout(
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = true,
                        startToEnd = { onClick(routine) },
                        endToStart = { onDeleted(routine) }
                    ) { RoutineItemCard(routine, onClick = onClick, onDeleted = onDeleted) }
                }
            }
        } else {
            EmptyList("روتینی برای روز $currentDay ثبت نکرده اید! ")
        }
    }


}

fun filterRoutinesByDay(day: String, routines: List<RoutineItem>): List<RoutineItem> {
    return routines.filter { it.days.contains(day) }
}

fun getOldId(routines: List<RoutineItem>): Int {
    return routines.maxOfOrNull { it.id } ?: 1000
}
