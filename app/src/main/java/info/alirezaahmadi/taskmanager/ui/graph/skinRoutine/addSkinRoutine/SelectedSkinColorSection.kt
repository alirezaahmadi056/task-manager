package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedSkinColorSection(
    currentColor: Int,
    onColor: (Int) -> Unit
) {

    val coorsList = Constants.skinColors
    Text(
        text = stringResource(R.string.selected_color),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 4.dp)
    )
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        coorsList.forEachIndexed { index, color ->
            SingleColor(
                color = color, isSelected = index == currentColor
            ) { onColor(index) }
        }
    }
}

@Composable
private fun SingleColor(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(45.dp)
                .clip(CircleShape)
                .background(color)
                .clickable(onClick = onClick)
        )
        Spacer(Modifier.height(6.dp))
        AnimatedVisibility(
         visible =  isSelected
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }


    }
}