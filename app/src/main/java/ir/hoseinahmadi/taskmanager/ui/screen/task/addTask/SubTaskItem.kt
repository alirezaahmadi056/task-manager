package ir.hoseinahmadi.taskmanager.ui.screen.task.addTask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.task.Task

@Composable
fun SubTaskItem(
    item: Task,
    onClick: () -> Unit,
    onCompeted: (Boolean) -> Unit
) {


    Card(
        enabled = !item.isCompleted,
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.scrim.copy(0.1f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
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
                onCheckedChange = {
                    onCompeted(it)
                })
        }
    }

}