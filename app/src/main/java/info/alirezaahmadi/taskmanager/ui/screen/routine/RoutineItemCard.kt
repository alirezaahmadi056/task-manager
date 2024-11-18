package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.util.TaskHelper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoutineItemCard(
    item: RoutineItem,
    onDeleted: (RoutineItem) -> Unit,
    onClick: (RoutineItem) -> Unit,
) {
    val routineColor = when (item.taskColor) {
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

    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, Color.LightGray.copy(0.6f)),
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(90.dp)
            .combinedClickable(
                onClick = { onClick(item) },
                onLongClick = { onDeleted(item) },
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(routineColor)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.weight(0.7f),
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.scrim,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 12.dp, bottom = 6.dp),
                text = TaskHelper.taskByLocate(item.time),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.scrim,
            )

        }

    }
}