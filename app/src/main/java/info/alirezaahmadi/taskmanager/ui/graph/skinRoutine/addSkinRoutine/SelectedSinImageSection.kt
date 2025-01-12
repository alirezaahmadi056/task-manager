package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedSinImageSection(
    currentImageIndex: Int,
    onImageIndex: (Int) -> Unit,
) {
    val imageLists = Constants.SkinsImage
    Text(
        text = stringResource(R.string.selected_image),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 4.dp)
    )
    FlowRow(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 12.dp),
        maxLines = 2,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        imageLists.fastMapIndexed { index, i ->
            SingleImage(
                image = i,
                isSelected = currentImageIndex==index
            ){
                onImageIndex(index)
            }
        }
    }
}

@Composable
private fun SingleImage(
    image: Int,
    isSelected: Boolean,
    onClick:()->Unit,
) {
    val boarderColor by animateColorAsState(
        targetValue = if (isSelected) Color.DarkGray else Color.White,
        label = ""
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .size(60.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, color = boarderColor, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            painter = painterResource(image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}