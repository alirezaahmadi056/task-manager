package info.alirezaahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlarmOn
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.util.TaskHelper
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItemCard(
    onClick: () -> Unit,
    onLogClick: (() -> Unit)? = null,
    item: TaskItem
) {

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
    val completedSubtasks = item.subTask.count { it.isCompleted }
    val totalSubtasks = item.subTask.size
    val pendingSubtasks = totalSubtasks - completedSubtasks
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(item.subTask) {
        progress = if (totalSubtasks > 0) {
            completedSubtasks.toFloat() / totalSubtasks
        } else {
            0f
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, Color.LightGray.copy(0.6f)),
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(110.dp)
            .combinedClickable(
                enabled = !hasNavigated,
                onClick = {
                    hasNavigated = true
                    onClick()
                },
                onLongClick = onLogClick,
            ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
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
                        modifier = Modifier.size(65.dp),
                        color = taskColor,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Butt,
                        trackColor = taskColor.copy(alpha = 0.2f),
                    )
                    Text(
                        text = "${
                            TaskHelper.taskByLocate(
                                (progress * 100).roundToInt().toString()
                            )
                        }${"%"}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.scrim,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 4.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textDecoration = if (progress == 1f) TextDecoration.LineThrough else TextDecoration.None,
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.TaskAlt,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(17.dp)
                            )

                            Text(
                                text = "${TaskHelper.taskByLocate(completedSubtasks.toString())} وظیفه",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        if (pendingSubtasks != 0) {
                            VerticalDivider(
                                modifier = Modifier
                                    .height(15.dp)
                                    .padding(horizontal = 10.dp),
                                thickness = 0.8.dp,
                                color = Color.LightGray
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PendingActions,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(17.dp)
                                )
                                Text(
                                    text = "${TaskHelper.taskByLocate(pendingSubtasks.toString())} وظیفه ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }


                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        if (progress == 1f) {
                            Row(
                                modifier = Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = TaskHelper.taskByLocate(item.completedTime),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.CheckCircleOutline,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                VerticalDivider(
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .height(17.dp)
                                        .padding(horizontal = 5.dp),
                                    color = MaterialTheme.colorScheme.scrim
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = TaskHelper.taskByLocate(item.createTime),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Create,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                            }
                        } else {
                            Row(
                                modifier = Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = TaskHelper.taskByLocate(item.createTime),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                                )
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Create,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.scrim.copy(0.8f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                        }

                    }

                }

            }
            if (item.triggerAlarmTime > System.currentTimeMillis()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AlarmOn,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }


}


