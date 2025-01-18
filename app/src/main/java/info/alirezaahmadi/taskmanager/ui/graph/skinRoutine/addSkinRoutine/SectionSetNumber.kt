package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@Composable
fun SectionSetNumber(
    enableDropDown: Boolean?,
    onDropDown: (Boolean?) -> Unit,
    setNumber: Int,
    onSetNumberChange: (Int) -> Unit,
    repetitionSetNumber: Int,
    onRepetitionSetNumberChange: (Int) -> Unit,
    timeNumber: Int,
    onTimeNumber: (Int) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NumberSection(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 5.dp),
            text = stringResource(R.string.set_number),
            maxNumber = 100,
            onNumberChange = onSetNumberChange,
            number = setNumber,
            enable = true
        )
        NumberSection(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 5.dp),
            text = stringResource(R.string.repetition_set_number),
            maxNumber = 500,
            onNumberChange = onRepetitionSetNumberChange,
            number = repetitionSetNumber,
            enable = enableDropDown == true || enableDropDown == null
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = enableDropDown == false,
                onCheckedChange = {
                    if (enableDropDown == false) {
                        onDropDown(null)
                    } else {
                        onRepetitionSetNumberChange(0)
                        onDropDown(false)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.DarkGray,
                    checkedColor = Color(0xff9747FF),
                )
            )
            Text(
                text = stringResource(R.string.to_disability),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(Modifier.width(12.dp))

        // "Dropdown" Section
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = enableDropDown == true,
                onCheckedChange = {
                    if (enableDropDown == true) {
                        onDropDown(null)
                    } else {
                        onDropDown(true)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.DarkGray,
                    checkedColor = Color(0xff9747FF)
                )
            )
            Text(
                text = stringResource(R.string.Dropdown),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                )
        }
    }
    NumberSection(
        modifier = Modifier.fillMaxWidth(0.5f),
        text = stringResource(R.string.Approximate_time),
        maxNumber = 600,
        onNumberChange = onTimeNumber,
        number = timeNumber,
        enable = true,
        onChangeSteep = 5
    )
}

@Composable
private fun NumberSection(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean,
    number: Int,
    onNumberChange: (Int) -> Unit,
    maxNumber: Int,
    onChangeSteep: Int = 1,
) {
    val enableBackgroundColor by animateColorAsState(
        targetValue = if (enable) Color(0xffECECEC)
        else Color(0xffECECEC).copy(0.4f),
        label = "",
    )

    val enableTextColor by animateColorAsState(
        targetValue = if (enable) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
            0.4f
        ),
        label = "",
    )
    val enableContentTextColor by animateColorAsState(
        targetValue = if (enable) Color.Black else Color.DarkGray,
        label = "",
    )

    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = enableTextColor
        )
        Spacer(Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(enableBackgroundColor, RoundedCornerShape(12.dp))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                enabled = enable && number < maxNumber,
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onNumberChange(number.plus(onChangeSteep)) }) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xff9747FF)
                )
            }
            Text(
                text = number.toString().byLocate(),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                fontWeight = FontWeight.Bold,
                color = enableContentTextColor
            )
            IconButton(
                enabled = enable && number > 0,
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onNumberChange(number.minus(onChangeSteep)) }) {
                Icon(
                    painter = painterResource(R.drawable.mines),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xff9747FF)
                )
            }


        }
    }

}