package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.ui.component.SelectedSortNotList
import ir.hoseinahmadi.taskmanager.util.Constants
import ir.hoseinahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun TaskScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    val item by taskViewModel.allItem.collectAsState(initial = emptyList())
    var sortOrder by remember { mutableIntStateOf(Constants.SORT_TASK) }

    // مرتب‌سازی آیتم‌ها بر اساس sortOrder
    val sortedNotesItem = when (sortOrder) {
        1 -> item.sortedBy { it.taskColor } // اولویت کم
        2 -> item.sortedByDescending { it.taskColor == 2 } // اولویت معمولی
        3 -> item.sortedByDescending { it.taskColor } // اولویت زیاد
        else -> item.reversed() //  حالت پیش ‌فرض بر اساس اخرین یادداشت
    }

    val (completedTasks, incompleteTasks) = sortedNotesItem.partition { sort ->
        sort.subTask.all { it.isCompleted }
    }
    SelectedSortNotList(false, noteSort = {}, taskSort = { selectedSort ->
        sortOrder = selectedSort

    }
    )
    Scaffold(

        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = true,
                text = {
                    Text(
                        text = "وظیقه",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.NoteAdd,
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                onClick = { navHostController.navigate(Screen.AddTaskScreen.route) })
        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            items(incompleteTasks) { taskItem ->
                TaskItemCard(navHostController = navHostController, item = taskItem)
            }

            item {
                CompletedTaskSection(navHostController, completedTasks)
            }

        }

    }


}


