package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout

@OptIn(ExperimentalFoundationApi::class)
@Preview(locale = "fa")
@Composable
fun SkinRoutineItemCard(
    item: SkinRoutineItem= SkinRoutineItem(title = "ddddd", status = "", description = "46546645456645456\nagdsygyia\nsadgy"),
    onEdited: () -> Unit={},
    onDeleted: () -> Unit={},
) {
    SwipeToDismissBoxLayout(
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true,
        startToEnd = onEdited,
        endToStart = onDeleted,
    ) {
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim.copy(0.2f)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 4.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onEdited,
                    onLongClick = onDeleted
                ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    text = item.description,
                    style = MaterialTheme.typography.titleLarge.copy(
                        lineHeight = 19.sp
                    ),
                    color = MaterialTheme.colorScheme.scrim.copy(0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }
        }
    }
}