package info.alirezaahmadi.taskmanager.ui.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import info.alirezaahmadi.taskmanager.data.model.about.AboutResponse

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun AboutHeaderSection(
    item: AboutResponse,
) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            GlideImage(
                modifier = Modifier.weight(0.4f),
                model = item.data.avatar,
                contentDescription = "",
            )
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${item.data.first_name} ${item.data.last_name}",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(7.dp))
                Detail("مدیر دانشجویار")
                Detail("مدرس برتر دانشجویار")
            }

        }


    }



@Composable
private fun Detail(
    title: String,
) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = "",
            tint = Color.Green
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.scrim,
        )
    }
}