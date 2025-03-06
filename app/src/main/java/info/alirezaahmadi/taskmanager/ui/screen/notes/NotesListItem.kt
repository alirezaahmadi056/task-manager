package info.alirezaahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.util.TaskHelper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesListItem(
    onClick: () -> Unit,
    onLogClick: (() -> Unit)? = null,
    item: NotesItem) {

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
    var hasNavigated by remember { mutableStateOf(false) }

    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim.copy(0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 5.dp)
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
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(taskColor)
            )
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 18.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp),
                    text = item.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.scrim.copy(0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 3.dp, bottom = 2.dp),
                        text = TaskHelper.taskByLocate("${item.createTime} ${item.createDate}"),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.scrim,
                        textAlign = TextAlign.End,
                    )
                }


            }


        }

    }
}
