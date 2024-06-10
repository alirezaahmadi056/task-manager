package ir.hoseinahmadi.taskmanager.ui.screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.taskmanager.data.model.Course

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseItemCard(item: Course) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GlideImage(
                model = item.thumbnail,
                contentDescription = "",
                modifier = Modifier.padding(4.dp).fillMaxWidth().height(100.dp)
                .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 2.dp),
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.scrim
                )
        }
    }
}