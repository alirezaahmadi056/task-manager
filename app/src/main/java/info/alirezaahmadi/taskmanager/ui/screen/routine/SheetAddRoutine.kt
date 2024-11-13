package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.ui.component.SetAlarmSection
import info.alirezaahmadi.taskmanager.ui.theme.font_bold
import info.alirezaahmadi.taskmanager.util.PersianDate
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.util.TaskHelper.splitWholeDate
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.RoutineViewModel
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SheetAddRoutine(
    show: Boolean,
    routineItem: RoutineItem?,
    routineViewModel: RoutineViewModel,
    days: List<String>,
    snackbarHostState: SnackbarHostState,
    lastId: Int,
    onDismissRequest: () -> Unit,
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    if (!show) return
    val dates by remember { mutableStateOf(PersianDate()) }
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    val selectedDayList = remember { mutableStateListOf<String>() }
    var enableAlarm by remember { mutableStateOf(false) }
    var selectedAlarmDataList by rememberSaveable { mutableStateOf(listOf(0, 0, 0)) }
    var selectedTimeHour by rememberSaveable { mutableIntStateOf(7) }
    var selectedTimeMinute by rememberSaveable { mutableIntStateOf(0) }
    val openDialogDate = remember { mutableStateOf(false) }
    val openDialogTime = remember { mutableStateOf(false) }

    LaunchedEffect(routineItem) {
        routineItem?.let { routine ->
            title = routine.title
            enableAlarm = System.currentTimeMillis() < routine.triggerAlarmTime
            selectedAlarmDataList =
                TaskHelper.convertMillisToDateList(routine.triggerAlarmTime)
            selectedDayList.apply {
                clear()
                addAll(routine.days)
            }
            val time = TaskHelper.convertMillisToTimeList(routine.triggerAlarmTime)
            selectedTimeHour = time[0]
            selectedTimeMinute = time[1]
        }
    }
    CustomDataPickerDialog(
        initialMinute = selectedTimeMinute,
        initialHour = selectedTimeHour,
        isShow = openDialogTime.value,
        onSelected = {
            selectedTimeHour = it.hour
            selectedTimeMinute = it.minute
        },
        onDismissRequest = {
            openDialogTime.value = false
        }
    )
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
            openDialog = openDialogDate,
            onSelectDay = {

            },
            onConfirm = {
                val date = TaskHelper.jalaliToGregorian(it.year, it.month, it.day)
                selectedAlarmDataList = splitWholeDate(date)
            },
            fontFamily = font_bold
        )
    }
    ModalBottomSheet(
        shape = RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        dragHandle = {
            DragHandle(
                isUpdate = routineItem != null,
                onDismissRequest = onDismissRequest,
                onSaved = {
                    if (enableAlarm) {
                        val calendar = Calendar.getInstance()
                        val triggerTime = TaskHelper.getTimeInMillis(
                            calendar = calendar,
                            year = selectedAlarmDataList[0],
                            month = selectedAlarmDataList[1],
                            day = selectedAlarmDataList[2],
                            hour = selectedTimeHour,
                            minute = selectedTimeMinute,
                        )
                        if (triggerTime < System.currentTimeMillis()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "زمان یادآور باید از آینده باشد!",
                                    withDismissAction = true
                                )
                            }
                        } else if (title.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "عنوان روتین نمی تواند خالی باشد",
                                    withDismissAction = true
                                )
                            }
                        } else if (selectedDayList.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "حداقل یک روز هفته را انتخاب کنید",
                                    withDismissAction = true
                                )
                            }
                        } else {
                            routineViewModel.upsertRoutine(
                                RoutineItem(
                                    id = if (routineItem?.id == null) lastId + 1 else routineItem.id,
                                    title = title,
                                    days = selectedDayList,
                                    triggerAlarmTime = triggerTime
                                )
                            )
                            onDismissRequest()
                        }
                    } else {
                         if (title.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "عنوان روتین نمی تواند خالی باشد",
                                    withDismissAction = true
                                )
                            }
                        } else if (selectedDayList.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "حداقل یک روز هفته را انتخاب کنید",
                                    withDismissAction = true
                                )
                            }
                        } else {
                            routineViewModel.upsertRoutine(
                                RoutineItem(
                                    id = if (routineItem?.id == null) lastId + 1 else routineItem.id,
                                    title = title,
                                    days = selectedDayList
                                )
                            )
                            onDismissRequest()
                        }

                    }


                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = title,
                onValueChange = { title = it },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.scrim.copy(0.8f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                ),
                label = {
                    Text(
                        "روتین",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                placeholder = {
                    Text(
                        "عنوان روتین .......",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 2,
            )

            Text(
                "روز های هفته",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 15.dp, bottom = 8.dp, start = 4.dp)
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                days.forEach { txt ->
                    val selected = selectedDayList.contains(txt)
                    SingleDay(
                        selected = selected,
                        day = txt[0].toString(),
                        onClick = {
                            if (selected) {
                                selectedDayList.remove(txt)
                            } else {
                                selectedDayList.add(txt)
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            SetAlarmSection(
                enableAlarm = enableAlarm,
                onSelectedDate = { openDialogDate.value = true },
                onSelectedTime = { openDialogTime.value = true },
                onEnable = { enb -> enableAlarm = enb },
                times = "${selectedTimeHour}:${selectedTimeMinute}",
                dates = TaskHelper.gregorianToJalali(
                    selectedAlarmDataList[0],
                    selectedAlarmDataList[1],
                    selectedAlarmDataList[2]
                ),
            )
            Spacer(Modifier.imePadding())
            Spacer(Modifier.height(20.dp))
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}


@Composable
private fun SingleDay(
    selected: Boolean, day: String,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.scrim
        else Color.White, label = ""
    )
    val backColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.background else
            MaterialTheme.colorScheme.primary, label = ""
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, textColor.copy(0.6f), RoundedCornerShape(14.dp))
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

@Composable
private fun DragHandle(
    isUpdate: Boolean = false,
    onDismissRequest: () -> Unit,
    onSaved: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isUpdate) "ویرایش روتین" else "افزودن روتین",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.scrim
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onDismissRequest
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.scrim
            )
        }
        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onSaved
        ) {
            Text(
                text = "ذخیره",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold, fontSize = 17.sp)
            )
        }
    }
}