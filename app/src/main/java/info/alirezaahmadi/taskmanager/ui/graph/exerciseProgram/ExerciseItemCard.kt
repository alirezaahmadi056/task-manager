package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import android.net.Uri
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@Preview(locale = "fa")
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ExerciseItemCard(
    item: ExerciseProgramItem = ExerciseProgramItem.fakeItem,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xffECECEC), RoundedCornerShape(12.dp))
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 8.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = Uri.parse(item.imageUri),
            contentDescription = "",
            modifier = Modifier
                .weight(0.2f)
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        ) {
            it.placeholder(R.drawable.ic_launcher_background)
        }
        Spacer(Modifier.weight(0.05f))
        Column(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxSize(),
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                BottomInfo(
                    icon = R.drawable.b,
                    text = "${
                        item.setNumber.toString().byLocate()
                    }ست ${item.repetitionSetNumber.toString().byLocate()} تایی"
                )
                Spacer(Modifier.width(8.dp))
                BottomInfo(
                    icon = R.drawable.stopwatch_progress,
                    text = "زمان تقریبی: ${item.time.toString().byLocate()} دقیقه"
                )

            }
        }
    }
}

@Composable
private fun BottomInfo(
    @DrawableRes
    icon: Int,
    text: String,
) {
    val tint = Color(0xff5A697D)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(17.dp),
            tint = tint
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = tint,
            fontWeight = FontWeight.SemiBold
        )
    }
}