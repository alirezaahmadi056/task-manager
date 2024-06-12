package ir.hoseinahmadi.taskmanager.ui.screen.about

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.taskmanager.data.model.about.Course

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseItemCard(item: Course) {
    if (item.ID!=3354454){
        val utiHandle = LocalUriHandler.current
        val context = LocalContext.current

        val color =if(item.is_gold) Color(0xFFFF9800) else Color.LightGray.copy(0.3f)
        Card(
            onClick = {
                try {
                    utiHandle.openUri(item.url)
                }catch (e:Exception){
                    Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
                }
            },
            border = BorderStroke(1.dp,color),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(200.dp)
                .padding(4.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
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

}