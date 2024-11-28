package info.alirezaahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FastTaskItemCard(
    taskItem: TaskItem,
    onIsCompletedClick: (Boolean) -> Unit,
    onLongClick: () -> Unit,
    isCompleted: Boolean = false
) {
    val textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, Color.LightGray.copy(0.6f)),
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(80.dp)
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = { onIsCompletedClick(!taskItem.subTask[0].isCompleted) }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                taskItem.subTask[0].title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textDecoration = textDecoration,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    checkmarkColor = Color.White,
                    uncheckedColor = MaterialTheme.colorScheme.scrim
                ),
                checked = taskItem.subTask[0].isCompleted,
                onCheckedChange = {
                    onIsCompletedClick(it)
                }
            )
        }
    }
}