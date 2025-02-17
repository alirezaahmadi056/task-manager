package ir.lrn.kara.ui.graph.skinRoutine

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
import androidx.compose.runtime.remember
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
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineItem
import ir.lrn.kara.util.Constants
import ir.lrn.kara.util.TaskHelper.byLocate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkinRoutineItemCard(
    item: SkinRoutineItem,
    onEdited: () -> Unit = {},
    onDeleted: () -> Unit = {},
) {

    val backgroundColor = remember(item.color) { Constants.skinColors[item.color] }
    val image = remember(item.image) { Constants.SkinsImage[item.image]  }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable (onClick = onEdited, onLongClick = onDeleted)
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
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground
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

