package info.alirezaahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val showBottomSheetAddTask = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddTask(
    obClick: (title: String) -> Unit
) {
    val show by remember { showBottomSheetAddTask }

    if (show) {
        var taskTitle by remember { mutableStateOf("") }
        ModalBottomSheet(
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showBottomSheetAddTask.value = false }) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    placeholder = {
                        Text(
                            text = "عنوان وظیفه را وارد کنید",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim.copy(0.5f)
                        )
                    },
                    maxLines = 2,
                    shape = RoundedCornerShape(11.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.scrim.copy(0.1f),
                        focusedContainerColor = MaterialTheme.colorScheme.scrim.copy(0.06f),
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    value = taskTitle,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    onValueChange = { taskTitle = it })
                Button(
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    onClick = {
                        obClick(taskTitle)
                    }
                ) {
                    Text(
                        text = "افزودن وظیفه",
                        style = MaterialTheme.typography.bodyLarge
                    )
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

        ModalBottomSheet(
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showBottomUpdateSheetTask.value = false }) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    placeholder = {
                        Text(
                            text = "وظیفه جدید را وارد کنید",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim.copy(0.5f)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.scrim.copy(0.07f),
                        focusedContainerColor = MaterialTheme.colorScheme.scrim.copy(0.05f),
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    ),
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    value = taskTitle,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    shape = RoundedCornerShape(15.dp),

                    onValueChange = { taskTitle = it })
                Button(
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    onClick = {
                        obClick(taskTitle)
                        showBottomUpdateSheetTask.value = false
                    }
                ) {
                    Text(
                        text = "ویرایش وظیفه",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}