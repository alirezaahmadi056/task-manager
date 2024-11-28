package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.ui.component.SetAlarmRoutine
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.RoutineViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SheetAddRoutine(
    show: Boolean,
    routineItem: RoutineItem?,
    routineViewModel: RoutineViewModel,
    days: List<String>,
    lastId: Int,
    onDismissRequest: () -> Unit,
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    if (!show) return
    var showDialogSelectedTime by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("7:00") }
    CustomDataPickerDialog(
        isShow = showDialogSelectedTime,
        initialMinute = 0,
        initialHour = 7,
        onDismissRequest = { showDialogSelectedTime = false },
        onSelected = {
            val formattedMinute = String.format(Locale.US, "%02d", it.minute)
            selectedTime = "${it.hour}:${formattedMinute}"
        }
    )

    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    val selectedDayList = remember { mutableStateListOf<String>() }
    var enableAlarm by remember { mutableStateOf(false) }
    var selectedTimeHour by rememberSaveable { mutableIntStateOf(7) }
    var selectedTimeMinute by rememberSaveable { mutableIntStateOf(0) }
    val openDialogTime = remember { mutableStateOf(false) }
    var checkInput by remember { mutableStateOf(false) }
    var currentTaskColor by remember { mutableIntStateOf(1) }
    val sheetState = rememberModalBottomSheetState(true)
    LaunchedEffect(routineItem) {
        routineItem?.let { routine ->
            title = routine.title
            currentTaskColor = routine.taskColor
            enableAlarm = routine.enableAlarm
            selectedTime =routine.time
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
    ModalBottomSheet(
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        dragHandle = {
            DragHandle(
                isUpdate = routineItem != null,
                onDismissRequest = onDismissRequest,
                onSaved = {
                    checkInput = true
                    if (enableAlarm) {
                        val triggerTime = TaskHelper.getTriggerTimeInMillis(
                            hour = selectedTimeHour,
                            minute = selectedTimeMinute,
                        )
                        if (title.isNotEmpty() && selectedDayList.isNotEmpty()) {
                            val routine = RoutineItem(
                                id = if (routineItem?.id == null) lastId + 1 else routineItem.id,
                                title = title,
                                days = selectedDayList,
                                triggerAlarmTime = triggerTime,
                                enableAlarm = true,
                                taskColor = currentTaskColor,
                                time = selectedTime
                            )
                            alarmViewModel.cancelWeeklyAlarms(context, routine)
                            alarmViewModel.setWeeklyAlarms(
                                context = context,
                                routineItem = routine,
                            )
                            routineViewModel.upsertRoutine(routine)
                            onDismissRequest()
                        }
                    } else {
                        if (title.isNotEmpty() && selectedDayList.isNotEmpty()) {
                            val routine = RoutineItem(
                                id = if (routineItem?.id == null) lastId + 1 else routineItem.id,
                                title = title,
                                days = selectedDayList,
                                taskColor = currentTaskColor,
                                time = selectedTime
                            )
                            alarmViewModel.cancelWeeklyAlarms(context, routine)
                            routineViewModel.upsertRoutine(routine)
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
                .animateContentSize()
                .verticalScroll(rememberScrollState()),
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
                    errorContainerColor = MaterialTheme.colorScheme.background,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    errorIndicatorColor = MaterialTheme.colorScheme.error,
                    errorPlaceholderColor = MaterialTheme.colorScheme.error,
                    errorSupportingTextColor = MaterialTheme.colorScheme.error,
                ),
                label = {
                    Text(
                        "روتین",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                supportingText = {
                    AnimatedVisibility(
                        checkInput && title.isEmpty()
                    ) {
                        Text(
                            "عنوان روتین را مشخص کنید",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                },
                isError = checkInput && title.isEmpty(),
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
            AnimatedVisibility(
                checkInput && selectedDayList.isEmpty()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    text = "حداقل یک روز هفته را انتخاب کنید",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start
                )
            }

            Text(
                "اولویت روتین",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 15.dp, bottom = 8.dp, start = 4.dp)
            )
            SectionSelectedColorRoutine(
                currentColor = currentTaskColor,
                onColor = { currentTaskColor = it }
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .clickable { showDialogSelectedTime =true }
                    .fillMaxWidth()
                    .padding(10.dp,),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector =  Icons.Rounded.AccessTime,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "ساعت شروع",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            .5.dp,
                            color = MaterialTheme.colorScheme.scrim,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = selectedTime,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            val formattedMinute = String.format(Locale.US, "%02d", selectedTimeMinute)
            SetAlarmRoutine(
                enableAlarm = enableAlarm,
                onSelectedTime = { openDialogTime.value = true },
                onEnable = { enb -> enableAlarm = enb },
                times = "${selectedTimeHour}:${formattedMinute}",
            )
            Spacer(Modifier.imePadding())
            Spacer(Modifier.height(15.dp))
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