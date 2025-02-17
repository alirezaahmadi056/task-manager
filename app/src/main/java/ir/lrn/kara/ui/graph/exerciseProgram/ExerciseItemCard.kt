package ir.lrn.kara.ui.graph.exerciseProgram

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.lrn.kara.R
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramItem
import ir.lrn.kara.ui.component.BaseImageLoader
import ir.lrn.kara.util.TaskHelper.byLocate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseItemCard(
    item: ExerciseProgramItem,
    onClick: () -> Unit ,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(12.dp))
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 8.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseImageLoader(
            model = Uri.parse(item.imageUri),
            modifier = Modifier
                .weight(0.2f)
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.weight(0.05f))
        Column(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxSize(),
        ) {
            Spacer(Modifier.height(5.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                val repetitionSetText = if(item.dropdown==false)
                    "تا ناتوانی"
                else "ست ${item.repetitionSetNumber.toString().byLocate()} تایی"
                ExerciseItemCardBottomInfo(
                    icon = R.drawable.b,
                    text = "${
                        item.setNumber.toString().byLocate()
                    } $repetitionSetText",
                )
                Spacer(Modifier.width(8.dp))
                ExerciseItemCardBottomInfo(
                    icon = R.drawable.stopwatch_progress,
                    text = "زمان تقریبی: ${item.time.toString().byLocate()} دقیقه"
                )

            }
        }
    }
}

@Composable
fun ExerciseItemCardBottomInfo(
    @DrawableRes
    icon: Int,
    text: String,
    bigText: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(if (bigText) 25.dp else 17.dp),
            tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
                .copy(fontSize = if (bigText) 16.sp else 12.sp),
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
            fontWeight = FontWeight.SemiBold
        )
    }
}