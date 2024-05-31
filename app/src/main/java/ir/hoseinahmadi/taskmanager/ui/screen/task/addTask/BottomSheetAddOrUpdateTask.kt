package ir.hoseinahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.taskmanager.data.db.task.Task
import ir.hoseinahmadi.taskmanager.viewModel.TaskViewModel
val showBottomSheetAddTask = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddTask(
    obClick: (title: String) -> Unit
) {
    val show by remember { showBottomSheetAddTask }

    if (show) {
        var taskTitle by remember { mutableStateOf("") }

        ModalBottomSheet(onDismissRequest = { showBottomSheetAddTask.value = false }) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                TextField(value = taskTitle, onValueChange = { taskTitle = it })
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    onClick = {
                        obClick(taskTitle)
                        taskTitle = ""
                        showBottomSheetAddTask.value = false
                    }
                ) {
                    Text(text = "add task")
                }
            }
        }
    }
}

val showBottomUpdateSheetTask = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomUpdateSheetTask(
    title: String,
    obClick: (title: String) -> Unit
) {
    val show by remember { showBottomUpdateSheetTask }

    if (show) {
        var taskTitle by remember { mutableStateOf(title) }

        ModalBottomSheet(onDismissRequest = { showBottomUpdateSheetTask.value = false }) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                TextField(value = taskTitle, onValueChange = { taskTitle = it })
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    onClick = {
                        obClick(taskTitle)
                        showBottomUpdateSheetTask.value = false
                    }
                ) {
                    Text(text = "update task")
                }
            }
        }
    }
}