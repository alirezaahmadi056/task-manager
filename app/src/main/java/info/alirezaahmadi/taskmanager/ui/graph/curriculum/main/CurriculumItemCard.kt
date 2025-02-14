package info.alirezaahmadi.taskmanager.ui.graph.curriculum.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurriculumItemCard(
    item: CurriculumItem,
    onEdited: () -> Unit,
    onDelete: () -> Unit
) {
    SwipeToDismissBoxLayout(
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        startToEnd = onEdited,
        endToStart = onDelete,
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
                .combinedClickable (onClick = onEdited, onLongClick = onDelete)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.startTime.byLocate()} - ${item.endTime.byLocate()}",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(0.3f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(12.dp))
                    .padding(vertical = 14.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(6.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 4.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}