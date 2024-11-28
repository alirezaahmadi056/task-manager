package info.alirezaahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import info.alirezaahmadi.taskmanager.data.db.task.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubTaskItem(
    item: Task,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onCompeted: (Boolean) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.scrim.copy(0.1f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .combinedClickable(enabled = !item.isCompleted, onClick = onClick, onLongClick = onLongClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    textDecoration =
                    if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.fillMaxWidth(0.9f),
                    overflow = TextOverflow.Ellipsis,
                    text = item.title,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyLarge
                )

            }


            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    checkmarkColor = Color.White,
                    uncheckedColor = MaterialTheme.colorScheme.scrim
                ),
                checked = item.isCompleted,
                onCheckedChange = { onCompeted(it) })
        }
    }

}