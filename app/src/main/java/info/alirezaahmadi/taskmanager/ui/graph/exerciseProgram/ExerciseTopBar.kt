package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun ExerciseTopBar(
    allDayWeek: List<String>,
    currentPage: Int,
    onSelected: (Int) -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.my_exercise_program),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Black
            )
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
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
        targetValue = if (!isSelected) MaterialTheme.colorScheme.onBackground
        else Color.White, label = ""
    )
    val backColor by animateColorAsState(
        targetValue = if (!isSelected) MaterialTheme.colorScheme.background else
            Color(0xff9747FF), label = ""
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