package info.alirezaahmadi.taskmanager.ui.graph.medicine.addMedicine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.remember
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
fun SelectedMedicineStatusSection(
    currentSList: List<String>,
    onAddDay: (String) -> Unit,
    onRemoveDay: (String) -> Unit,
) {
    val dayList = remember { Constants.deyWeek }
    Text(
        text = stringResource(R.string.day_week),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(
            bottom = 12.dp,
            start = 10.dp
        ),
    )


    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 7,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center
    ) {
        dayList.forEach { day ->
            val selected = currentSList.contains(day)
            SingleDay(
                selected = selected,
                day = day[0].toString(),
                onClick = {
                    if (selected) {
                        onRemoveDay(day)
                    } else {
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
        targetValue = if (selected) Color.White
        else Color(0xff43A154), label = ""
    )
    val backColor by animateColorAsState(
        targetValue = if (!selected) Color.White else
            Color(0xff43A154), label = ""
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .border(
                border = BorderStroke(
                    1.dp,
                    Color(0xff43A154)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(backColor), contentAlignment = Alignment.Center
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