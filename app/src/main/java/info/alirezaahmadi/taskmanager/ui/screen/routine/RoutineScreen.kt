package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.RoutineViewModel

@Composable
fun RoutineScreen(
    routineViewModel: RoutineViewModel = hiltViewModel()
) {
    val allRoutine by routineViewModel.getAllRoutine().collectAsState(emptyList())
    val dayWeek by remember { mutableStateOf(Constants.deyWeek) }
    val pagerState = rememberPagerState { dayWeek.size }
    var singleRoutine by remember { mutableStateOf<RoutineItem?>(null) }
    var showSheetAddRoutine by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    SheetAddRoutine(
        show = showSheetAddRoutine,
        onDismissRequest = {
            showSheetAddRoutine = false
        },
        routineItem = singleRoutine,
        days = dayWeek,
        snackbarHostState = snackBarHostState,
        lastId = getOldId(allRoutine),
        routineViewModel = routineViewModel
    )
    Scaffold(
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
            TabSection(
                allTabs = dayWeek,
                pagerState = pagerState
            )
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Button(
                onClick = {
                    singleRoutine = null
                    showSheetAddRoutine = true
                }
            ) { }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pagerState
        ) { page ->
            Routine(filterRoutinesByDay(day = dayWeek[page], routines = allRoutine),
                onClick = { data ->
                    singleRoutine = data
                    showSheetAddRoutine = true
                }
            )
        }
    }
}

@Composable
private fun Routine(routines: List<RoutineItem>, onClick: (RoutineItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(routines) {
            RoutineItemCard(it, onClick = onClick, onDeleted = {})
        }
    }
}

fun filterRoutinesByDay(day: String, routines: List<RoutineItem>): List<RoutineItem> {
    return routines.filter { it.days.contains(day) }
}

fun getOldId(routines: List<RoutineItem>): Int {
    return routines.maxOfOrNull { it.id } ?: 0
}