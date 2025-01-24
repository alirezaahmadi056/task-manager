package info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsItemCard(
    item: GoalsItem,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Bold,
                color = Color(0xff5A697D),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xff5A697D),
                modifier = Modifier.fillMaxWidth(0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}