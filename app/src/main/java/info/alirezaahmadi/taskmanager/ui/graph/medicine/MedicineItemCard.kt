package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineItem
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineItemCard(
    item: MedicineItem,
    onEdited: () -> Unit,
    onDeleted: () -> Unit,
) {
    SwipeToDismissBoxLayout(
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true,
        startToEnd = onEdited,
        endToStart = onDeleted
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable(onClick = onEdited, onLongClick = onDeleted)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.time.byLocate(),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(0.2f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
            Row(
                modifier = Modifier
                    .weight(0.8f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.medicine_item_image),
                    contentDescription = "",
                    modifier = Modifier
                        .background(Color(0xffC3D8C7), RoundedCornerShape(12.dp))
                        .padding(4.dp)
                        .size(55.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}