package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.util.TaskHelper
import kotlin.math.roundToInt

@Composable
fun TaskItemCard(navHostController: NavHostController, item: TaskItem) {
    val taskColor = when (item.taskColor) {
        2 -> {
            MaterialTheme.colorScheme.onSecondary
        }

        3 -> {
            MaterialTheme.colorScheme.error
        }

        else -> {
            MaterialTheme.colorScheme.onPrimary
        }
    }

    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(item.subTask) {
        val completedSubtasks = item.subTask.count { it.isCompleted }
        val totalSubtasks = item.subTask.size
        progress = if (totalSubtasks > 0) {
            completedSubtasks.toFloat() / totalSubtasks
        } else {
            0f
        }
    }

    Card(
        colors =CardDefaults.cardColors(
            containerColor = if (progress==1f)MaterialTheme.colorScheme.scrim.copy(0.1f) else MaterialTheme.colorScheme.background,
            ),
        border = BorderStroke(0.8.dp, Color.LightGray),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(90.dp),
        onClick = { navHostController.navigate(Screen.AddTaskScreen.route+"?id=${item.id}") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(taskColor)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(60.dp),
                    color = taskColor,
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Butt,
                    trackColor = taskColor.copy(alpha = 0.2f),
                )

                Text(
                    text ="${TaskHelper.taskByLocate ((progress * 100).roundToInt().toString())}${"%"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                textDecoration = if(progress==1f) TextDecoration.LineThrough else TextDecoration.None,
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }



}