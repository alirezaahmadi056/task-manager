package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.model.SkinDayStatus

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedSkinStatusSection(
    currentStatus: String,
    onStatusSelectedStatus: (String) -> Unit
) {

    val statusList = SkinDayStatus.statusList
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 3,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        statusList.forEach {
            DayStatusItem(
                day = it,
                currentStatus = currentStatus,
                onStatusSelectedStatus = onStatusSelectedStatus
            )
        }
    }
}

@Composable
private fun DayStatusItem(
    day: SkinDayStatus,
    currentStatus: String,
    onStatusSelectedStatus: (String) -> Unit
) {
    val selected = day.id == currentStatus
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFFFE3D5) else Color.White,
        label = ""
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.Black else Color.Gray,
        label = ""
    )
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onStatusSelectedStatus(day.id) }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = if (selected) painterResource(day.selectedIcon) else painterResource(day.unSelectedIcon),
            contentDescription = "",
            tint = textColor
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = day.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}