package ir.hoseinahmadi.taskmanager.ui.screen.addNotes

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.taskmanager.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SaveImageCard(
    uri: Uri,
    addIconDelete: @Composable (() -> Unit)? = null,
) {

    val context = LocalContext.current

    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth(.5f)
            .padding(horizontal = 6.dp, vertical = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            addIconDelete?.invoke() // فراخوانی تابع اختیاری

            Column(
                modifier = Modifier
                    .padding(end = 8.dp, top = 3.dp)
                    .weight(0.6f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uri.path.toString(),
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uri.authority.toString(),
                    color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }

            GlideImage(
                model = uri,
                contentDescription = "Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = uri
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }
                        context.startActivity(intent)
                    },
                contentScale = ContentScale.Crop,
            ) {
                it.placeholder(R.drawable.document_ic)
            }


        }
    }
}