package ir.lrn.kara.ui.graph.duties

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
import ir.lrn.kara.data.model.NavItem
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.BaseTopBar
import ir.lrn.kara.ui.graph.duties.notes.NotesScreen
import ir.lrn.kara.ui.graph.duties.task.TaskScreen
import ir.lrn.kara.util.Constants
import ir.lrn.kara.viewModel.AlarmViewModel
import ir.lrn.kara.viewModel.NotesViewModel
import ir.lrn.kara.viewModel.TaskViewModel

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