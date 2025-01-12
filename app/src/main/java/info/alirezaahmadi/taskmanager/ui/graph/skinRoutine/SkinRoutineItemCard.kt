package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkinRoutineItemCard(
    item: SkinRoutineItem,
    onEdited: () -> Unit = {},
    onDeleted: () -> Unit = {},
) {

    val backgroundColor = Constants.skinColors[item.color]
    val image = Constants.SkinsImage[item.image]
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable (onClick = onEdited, onLongClick = onDeleted)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.time.byLocate(),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(0.2f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
            Row(
                modifier = Modifier
                    .weight(0.8f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(4.dp))
                Image(
                    painter = painterResource(image),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
            }
        }

    }

/*
Row(
modifier = Modifier.fillMaxWidth(),
verticalAlignment = Alignment.CenterVertically,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .weight(0.2f)
        ,
        contentAlignment = Alignment.Center
    ){
        Text(
            text = item.time,
            fontWeight = FontWeight.SemiBold,
        )
    }
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim.copy(0.2f)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .weight(0.8f)
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
}*/
