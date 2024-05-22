package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.NotesItem
import ir.hoseinahmadi.taskmanager.ui.theme.LightBlue
import ir.hoseinahmadi.taskmanager.ui.theme.LightGreen
import ir.hoseinahmadi.taskmanager.ui.theme.LightPurple

@Composable
fun NotesItem(item: NotesItem){
    val taskColor = listOf(LightPurple, LightBlue, LightGreen).random()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${item.startTime}\nAM",
            textAlign = TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .border(
                        border = BorderStroke(3.dp, Color.Black),
                        shape = CircleShape
                    )
            )
            HorizontalDivider(modifier = Modifier.width(6.dp), color = Color.Black)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = taskColor
                    ),
                    onClick = {},
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .weight(0.9f),
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(
                            top = 12.dp,
                            start = 12.dp
                        )
                    )
                    Text(
                        text = item.body!!,
                        modifier = Modifier.padding(
                            start = 12.dp
                        ), color = Color.Gray
                    )
                    Text(
                        text = "${item.startTime} - ${item.endTime}",
                        modifier = Modifier.padding(
                            start = 12.dp,
                            bottom = 12.dp
                        )
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .width(60.dp)
                        .weight(0.1f),
                    color = Color.Black
                )
            }
        }

    }
}