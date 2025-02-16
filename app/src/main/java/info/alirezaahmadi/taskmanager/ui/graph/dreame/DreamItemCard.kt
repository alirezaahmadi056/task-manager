package info.alirezaahmadi.taskmanager.ui.graph.dreame

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DreamItemCard(
    item: DreamItem,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {

    SwipeToDismissBoxLayout(
        enableDismissFromStartToEnd = !item.isCompleted,
        enableDismissFromEndToStart = true,
        startToEnd = onEdit,
        endToStart = onDeleted
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable(
                    enabled = !item.isCompleted,
                    onClick = onEdit,
                    onLongClick = onDeleted
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseImageLoader(
                model = Uri.parse(item.coverUri),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(0.28f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier
                    .weight(0.62f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color =  MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 9.dp),
                    overflow = TextOverflow.Ellipsis,
                    color =  MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.1f),
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "",
                tint =  MaterialTheme.colorScheme.onBackground
            )
        }

    }
}