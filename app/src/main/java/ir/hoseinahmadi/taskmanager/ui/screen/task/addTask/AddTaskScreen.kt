package ir.hoseinahmadi.taskmanager.ui.screen.task.addTask

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.task.Task
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.viewModel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    id: Int,
    taskViewModel: TaskViewModel = hiltViewModel()
) {

    var taskTitle by remember { mutableStateOf("") }
    var taskColor by remember { mutableIntStateOf(1) }
    var subTask by remember { mutableStateOf<List<Task>>(mutableListOf()) }

    var subTaskItem by remember { mutableStateOf(Task()) }
    var subTaskId by remember { mutableIntStateOf(0) }

    if (id != 0) {
        LaunchedEffect(key1 = id) {
            taskViewModel.getSingleTaskById(id).collectLatest { taskItem ->
                taskTitle = taskItem.title
                taskColor = taskItem.taskColor
                subTask = taskItem.subTask
            }
        }
    }

    BottomUpdateSheetTask(
        title = subTaskItem.title,
        obClick = { newTitle ->
            val updatedSubTasks = subTask.toMutableList().apply {
                this[subTaskId] = this[subTaskId].copy(title = newTitle)
            }
            subTask = updatedSubTasks
        }
    )

    BottomSheetAddTask { title ->
        subTask = subTask + Task(title = title)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
            }
        }
        itemsIndexed(subTask) { index, item ->
            SubTaskItem(item) {
                subTaskId = index
                subTaskItem = item
                showBottomUpdateSheetTask.value = true
            }
        }
        item {
            Button(onClick = { showBottomSheetAddTask.value = true }) {
                Text(text = "add task")
            }
        }
        item {
            Button(onClick = {
                val taskItem = TaskItem(
                    id = id,
                    title = taskTitle,
                    subTask = subTask,
                    taskColor = taskColor
                )
                taskViewModel.upsertTask(taskItem)
                navHostController.popBackStack()
            }) {
                Text(text = "Save Task")
            }
        }
    }
}