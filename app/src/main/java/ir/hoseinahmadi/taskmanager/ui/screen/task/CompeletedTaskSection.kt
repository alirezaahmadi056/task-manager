package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.util.TaskHelper

@Composable
fun CompletedTaskSection(
    navHostController: NavHostController,
    item: List<TaskItem>
) {
        var expanded by remember {
            mutableStateOf(false)
        }
        val rotateState by animateFloatAsState(
            targetValue = if (expanded) 180f else 0f,
            label = ""
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = " تکمیل شده",
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text ="( ${ TaskHelper.taskByLocate(item.size.toString())} ${"وظیفه )"}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )


            }


            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "",
                    modifier = Modifier
                        .rotate(rotateState),
                    tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                )
            }
        }




        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {
            Column {
                for (i in item) {
                    TaskItemCard(navHostController = navHostController, item = i)
                }

            }

        }
    }
