package info.alirezaahmadi.taskmanager.ui.screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.MainDrawer
import info.alirezaahmadi.taskmanager.navigation.BottomNavigation
import info.alirezaahmadi.taskmanager.topBarMain.DrawerContent
import info.alirezaahmadi.taskmanager.topBarMain.TopBar
import info.alirezaahmadi.taskmanager.ui.screen.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.screen.task.TaskScreen
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
    val pagerState = rememberPagerState { 2 }


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
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) { page ->
                    if (page == 1) {
                        TaskScreen(
                            navHostController = navHostController,
                            taskViewModel = taskViewModel,
                            alarmViewModel = alarmViewModel
                        )
                    } else {
                        NotesScreen(
                            navHostController = navHostController,
                            notesViewModel = notesViewModel
                        )
                    }
                }
            }
        })


}