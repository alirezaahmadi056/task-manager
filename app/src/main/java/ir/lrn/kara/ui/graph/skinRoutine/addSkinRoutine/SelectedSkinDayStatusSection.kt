package ir.lrn.kara.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.lrn.kara.util.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedSkinDayStatusSection(
    currentSList: List<String>,
    onAddDay: (String) -> Unit,
    onRemoveDay: (String) -> Unit,
) {
    val dayList = Constants.deyWeek
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 7,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center
    ) {
        dayList.forEach {day->
            val selected = currentSList.contains(day)
                SingleDay(
                   selected = selected,
                    day = day[0].toString(),
                    onClick = {
                        if (selected){
                            onRemoveDay(day)
                        }else{
                            onAddDay(day)
                        }
                    }
                )
            }

    }
}

@Composable
private fun SingleDay(
    selected: Boolean, day: String,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.Black
        else Color.Gray, label = ""
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFFFE3D5) else Color.White,
        label = ""
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(backgroundColor), contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            text = day,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }

}