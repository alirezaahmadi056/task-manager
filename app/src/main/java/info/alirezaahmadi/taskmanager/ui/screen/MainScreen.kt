package info.alirezaahmadi.taskmanager.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.ui.component.MainDrawer
import info.alirezaahmadi.taskmanager.navigation.BottomNavigation
import info.alirezaahmadi.taskmanager.topBarMain.DrawerContent
import info.alirezaahmadi.taskmanager.topBarMain.TopBar
import info.alirezaahmadi.taskmanager.ui.screen.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.screen.routine.RoutineScreen
import info.alirezaahmadi.taskmanager.ui.screen.task.TaskScreen
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navHostController: NavHostController,
    darkTheme: (Boolean) -> Unit,
    taskViewModel: TaskViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val pageIndex = when (context.intent.action) {
        Constants.ACTION_TASK_RECEIVER -> 1
        Constants.ACTION_ROUTINE_RECEIVER -> 2
        else -> 0
    }
    val pagerState = rememberPagerState(initialPage = pageIndex) { 3 }


    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MainDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navHostController,
                isOpen = drawerState.isOpen,
                changeThem = { darkTheme(it) },
                onFinish = {
                    scope.launch {
                        drawerState.close()
                    }
                },
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        navHostController = navHostController,
                        openDrawer = { scope.launch { drawerState.open() } },
                        pagerState = pagerState,
                    )
                },
                bottomBar = {
                    BottomNavigation(
                        pagerState = pagerState,
                        coroutineScope = scope
                    )
                },
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                HorizontalPager(
                    userScrollEnabled = false,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) { page ->
                    when (page) {
                        1 -> {
                            TaskScreen(
                                navHostController = navHostController,
                                taskViewModel = taskViewModel,
                                alarmViewModel = alarmViewModel
                            )
                        }

                        0 -> {
                            NotesScreen(
                                navHostController = navHostController,
                                notesViewModel = notesViewModel
                            )
                        }

                        else -> {
                            RoutineScreen()
                        }
                    }

                }
            }
        })


}