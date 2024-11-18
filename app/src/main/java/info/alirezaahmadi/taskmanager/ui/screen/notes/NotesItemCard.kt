package info.alirezaahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun NotesItemCard(
    item: NotesItem,
    onClick: () -> Unit,
    onLogClick: (() -> Unit)? = null,
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
    var hasNavigated by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.combinedClickable(
            enabled = !hasNavigated,
            onClick = {
                hasNavigated = true
                onClick()
            },
            onLongClick = onLogClick,
        ),
        colors = CardDefaults.cardColors(containerColor = taskColor),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = item.title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.scrim,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.scrim.copy(0.6f)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                text = TaskHelper.taskByLocate("${item.createTime} ${item.createDate}"),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.scrim
            )
        }


    }


}