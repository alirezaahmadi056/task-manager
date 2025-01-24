package info.alirezaahmadi.taskmanager.ui.graph.duties

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.model.NavItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.BaseTopBar
import info.alirezaahmadi.taskmanager.ui.graph.duties.notes.NotesScreen
import info.alirezaahmadi.taskmanager.ui.graph.duties.task.TaskScreen
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun DutiesScreen(
    navHostController: NavHostController,
    darkTheme: (Boolean) -> Unit,
    taskViewModel: TaskViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val pageIndex = when (context.intent.action) {
        Constants.ACTION_TASK_RECEIVER -> 1
        else -> 0
    }
    val scope = rememberCoroutineScope()
    val item = listOf(
        NavItem(
            selectedIcon = Icons.Rounded.NoteAlt,
            unSelectedIcon = Icons.Outlined.NoteAlt,
            text = "یادداشت",
        ),
        NavItem(
            selectedIcon = Icons.Rounded.Task,
            unSelectedIcon = Icons.Outlined.Task,
            text = "وظیفه",
        ),
        )
    val pagerState = rememberPagerState(initialPage = pageIndex) { 3 }
    Scaffold(
        topBar = {
            BaseTopBar(
                navHostController = navHostController,
                text =if (pagerState.currentPage==0)"یادداشت های من" else "وظایف من",
                searchIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.SearchScreen)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    }
                }
            )
        },
        bottomBar = {
            DutiesBottomNavigation(
                pagerState = pagerState,
                coroutineScope = scope,
                navItem = item
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
            if (page==1){
                TaskScreen(
                    navHostController = navHostController,
                    taskViewModel = taskViewModel,
                    alarmViewModel = alarmViewModel
                )
            }else{
                NotesScreen(
                    navHostController = navHostController,
                    notesViewModel = notesViewModel
                )
            }
        }
    }


}