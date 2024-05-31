package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem

@Composable
fun TaskItemCard(item:TaskItem){

    Card(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp),
        onClick = { /*TODO*/ }) {

    }
    var progress by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(item.subTask) {
        val completedSubtasks = item.subTask.count { it.isCompleted }
        val totalSubtasks = item.subTask.size
        progress = if (totalSubtasks > 0) {
            completedSubtasks.toFloat() / totalSubtasks * 100f
        } else {
            0f
        }
    }
}