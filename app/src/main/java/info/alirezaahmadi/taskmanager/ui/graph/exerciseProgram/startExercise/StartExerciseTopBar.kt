package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuOpen
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader
import info.alirezaahmadi.taskmanager.util.openUri

@Composable
fun StartExerciseTopBar(
    image: String,
    video: String,
    onBack: () -> Unit,
    openDialogListExercise:()->Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            BaseImageLoader(
                modifier = Modifier.fillMaxSize()
                    .clickable(enabled =image.isNotEmpty() ) { openUri(context,Uri.parse(image)) },
                contentScale = ContentScale.Crop,
                model = Uri.parse(image)
            )
            HeaderIcon(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 6.dp)
                        .size(28.dp)
                )
            }

            if (video.isNotEmpty()) {
                HeaderIcon(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onClick = {
                        openUri(context = context, uri = Uri.parse(video))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "",
                            tint = Color.White,
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.played_video),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }
                }
            }
            HeaderIcon(
                modifier = Modifier.align(Alignment.TopStart),
                onClick =openDialogListExercise
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.MenuOpen,
                        contentDescription = "",
                        tint = Color.White,
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.exercise_list),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
            }

        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray.copy(0.5f))
    }


}

@Composable
private fun HeaderIcon(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent.copy(0.7f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = content
    )
}