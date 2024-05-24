package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.NotesItem

@Composable
fun NotesListItem(item: NotesItem) {
    Card(
        elevation = CardDefaults.cardElevation(1.5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(2.dp),
        onClick = { /*TODO*/ }) {
        Box(
            modifier = Modifier
        ) {
            Box(modifier = Modifier
                .width(10.dp)
                .fillMaxHeight()
                .background(item.taskColor)
            )
            Column(
                modifier = Modifier.padding(4.dp).padding(start = 28.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.body!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }


        }

    }
}
