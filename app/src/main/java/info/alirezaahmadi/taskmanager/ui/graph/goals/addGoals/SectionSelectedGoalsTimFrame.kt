package info.alirezaahmadi.taskmanager.ui.graph.goals.addGoals

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame

@Composable
fun SectionSelectedGoalsTimFrame(
    currentTimeFrame: String,
    onSelectedTimeFrame: (String) -> Unit
) {
    val goalsTimeFrame = remember { GoalsTimeFrame.entries }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        goalsTimeFrame.forEach { timeFrame ->
            TimeFrameButton(
                name = timeFrame.perName,
                selected = currentTimeFrame == timeFrame.name,
                onClick = { onSelectedTimeFrame(timeFrame.name) }
            )
        }
    }

}

@Composable
private fun TimeFrameButton(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color(0xff535CF0),
        label = ""
    )
    val backGroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xff535CF0) else Color.White,
        label = ""
    )

    OutlinedButton(
        modifier = Modifier.padding(horizontal = 2.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xff535CF0)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backGroundColor,
            contentColor = textColor
        )
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

    }

}