package ir.lrn.kara.ui.graph.goals.addGoals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import ir.lrn.kara.R
import ir.lrn.kara.ui.theme.font_bold
import ir.lrn.kara.util.PersianDate
import ir.lrn.kara.util.TaskHelper.byLocate
import ir.huri.jcal.JalaliCalendar

@Composable
fun SectionSelectedGoalsDate(
    currentDate :String?,
    onSelectedDate: (String) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }
    val dates by remember { mutableStateOf(PersianDate()) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        JalaliDatePickerDialog(
            textColor = MaterialTheme.colorScheme.scrim,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            dropDownColor = MaterialTheme.colorScheme.scrim,
            backgroundColor = MaterialTheme.colorScheme.background,
            textColorHighlight = MaterialTheme.colorScheme.onSecondary,
            todayBtnColor = MaterialTheme.colorScheme.scrim,
            dayOfWeekLabelColor = MaterialTheme.colorScheme.scrim,
            confirmBtnColor = MaterialTheme.colorScheme.scrim,
            cancelBtnColor = MaterialTheme.colorScheme.error,
            disableBeforeDate = JalaliCalendar(dates.year, dates.month - 1, dates.day - 1),
            fontSize = 16.sp,
            openDialog = showDialog,
            onSelectDay = {},
            onConfirm = { onSelectedDate("${it.year}/${it.month}/${it.day}") },
            fontFamily = font_bold
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff535CF0))
                    .clickable { showDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(
                text = stringResource(R.string.selected_date),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

        }
        Text(
            text = currentDate?.byLocate()?:"انتخاب نشده",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { showDialog.value = true }
        )
    }
}