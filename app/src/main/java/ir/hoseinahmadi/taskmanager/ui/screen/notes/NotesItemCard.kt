package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.NotesItem
import ir.hoseinahmadi.taskmanager.ui.theme.LightBlue
import ir.hoseinahmadi.taskmanager.ui.theme.LightGreen
import ir.hoseinahmadi.taskmanager.ui.theme.LightPurple

@Composable
fun NotesItemCard(item: NotesItem) {
    val taskColor = listOf(LightPurple, LightBlue, LightGreen).random()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = item.taskColor
        ),
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = {},
    ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 12.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.scrim,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.body!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.scrim.copy(0.7f),

                    )
            }

//            Box(modifier = Modifier
//                .align(Alignment.TopEnd)
//                .size(15.dp)
//                .clip(RoundedCornerShape(bottomStart = 12.dp))
//                .background(Color.Red)
//            )
        }




}