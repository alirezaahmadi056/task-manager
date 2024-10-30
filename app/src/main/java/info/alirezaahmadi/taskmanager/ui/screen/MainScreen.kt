package info.alirezaahmadi.taskmanager.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.ui.screen.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.screen.task.TaskScreen
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
    pagerState: PagerState,
    taskViewModel: TaskViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
    ) { page ->
        if (page == 1) {
            TaskScreen(
                navHostController = navHostController,
                taskViewModel = taskViewModel,
                alarmViewModel = alarmViewModel
            )
        } else {
            NotesScreen(navHostController = navHostController, notesViewModel = notesViewModel)
        }
    }

}