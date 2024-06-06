package ir.hoseinahmadi.taskmanager.ui.screen.notes

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.util.TaskHelper

@Composable
fun NotesItemCard(navHostController: NavHostController, item: NotesItem) {
    val taskColor = when (item.taskColor) {
        2 -> { MaterialTheme.colorScheme.onSecondary }
        3 -> { MaterialTheme.colorScheme.error }
        else -> { MaterialTheme.colorScheme.onPrimary }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = taskColor),
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = {
            navHostController.navigate(Screen.AddNotesScreen.route + "?id=${item.id}")
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
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
                color = MaterialTheme.colorScheme.scrim.copy(0.7f),
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                text = TaskHelper.taskByLocate("${item.createTime} | ${item.createDate}"),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.scrim
            )
        }


    }


}