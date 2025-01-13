package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R

@Composable
fun SkinTopBar(
    allDayWeek: List<String>,
    currentPage: Int,
    onSelected: (Int) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.my_skin_routine),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 22.sp,
            ),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 25.dp, top = 8.dp),
            selectedTabIndex = currentPage,
            edgePadding = 19.dp,
            divider = {},
            indicator = {},
            containerColor = Color.Transparent
        ) {
            allDayWeek.forEachIndexed { index, dayName ->
                SingeTopDay(
                    dayName = dayName,
                    isSelected = index == currentPage,
                    onClick = { onSelected(index) })
            }
        }
    }
}

@Composable
private fun SingeTopDay(
    dayName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (!isSelected) Color.DarkGray
        else Color.Black, label = ""
    )
    val backColor by animateColorAsState(
        targetValue = if (!isSelected) Color.White else
            Color(0xffF3B99D), label = ""
    )

    ElevatedButton(
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(42.dp),
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
    ) {
        Text(
            text = dayName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}