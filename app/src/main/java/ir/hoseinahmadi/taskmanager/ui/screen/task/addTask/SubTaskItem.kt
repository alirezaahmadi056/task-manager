package ir.hoseinahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.task.Task
@Composable
fun SubTaskItem(
    item: Task,
    onClick: () -> Unit
) {
    AssistChip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { onClick() },
        label = { Text(text = item.title) }
    )
}