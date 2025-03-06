package info.alirezaahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.data.db.task.Task
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskItemType
import info.alirezaahmadi.taskmanager.ui.theme.LightGray
import info.alirezaahmadi.taskmanager.util.PersianDate
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun FastNoteSection(taskViewModel: TaskViewModel, id: Int) {
    val focusManager = LocalFocusManager.current

    var body by remember { mutableStateOf("") }

    val textFieldColor = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTextColor = MaterialTheme.colorScheme.scrim,
        cursorColor = Color(0xFF2196F3)
    )
    TextField(
        colors = textFieldColor,
        maxLines = 3,
        minLines = 1,
        placeholder = {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "نوشتن وظیفه سریع",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = Modifier.fillMaxWidth(),
        value = body,
        trailingIcon = {
            IconButton(
                enabled = body.isNotEmpty(),
                colors = IconButtonDefaults.iconButtonColors(
                    disabledContentColor = LightGray,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    taskViewModel.upsertTask(
                        TaskItem(
                            id = id + 1,
                            type = TaskItemType.FAST.name,
                            subTask = listOf(
                                Task(
                                    title = body,
                                    isCompleted = false
                                )
                            ),
                            taskColor = 3,
                        )
                    )
                    body = ""
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddBox,
                    contentDescription = "",
                    modifier = Modifier.size(27.dp)
                )
            }
        },
        onValueChange = { body = it },
        textStyle = MaterialTheme.typography.labelMedium
    )
}