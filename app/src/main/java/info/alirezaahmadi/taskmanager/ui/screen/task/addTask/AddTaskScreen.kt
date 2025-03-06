package info.alirezaahmadi.taskmanager.ui.screen.task.addTask

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import info.alirezaahmadi.taskmanager.data.db.task.Task
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.ui.component.SetAlarmSection
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout
import info.alirezaahmadi.taskmanager.ui.component.TopBar
import info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes.BottomSheetSelectedColor
import info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes.SheetSaveDiscard
import info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes.showBottomSheetSelectedColor
import info.alirezaahmadi.taskmanager.ui.theme.font_bold
import info.alirezaahmadi.taskmanager.util.PersianDate
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.util.TaskHelper.splitWholeDate
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    id: Int,
    lastId: Int,
    taskViewModel: TaskViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {

    val dates by remember { mutableStateOf(PersianDate()) }
    val focusManager = LocalFocusManager.current
    var showSheetDiscard by remember { mutableStateOf(false) }
    var enableAlarm by remember { mutableStateOf(false) }
    var taskTitle by remember { mutableStateOf("") }
    var selectedColor by remember { mutableIntStateOf(1) }
    val subTask = remember { mutableStateListOf<Task>() }
    var showDialogDeletedSubTask by remember { mutableStateOf(false) }
    var taskBody by remember { mutableStateOf("") }
    var createTime by remember { mutableStateOf("") }
    var completedTime by remember { mutableStateOf("") }

    var subTaskItem by remember { mutableStateOf(Task()) }
    var subTaskId by remember { mutableIntStateOf(0) }

    val oldSubTask = remember { mutableStateListOf<Task>() }
    var oldTaskTitle by remember { mutableStateOf("") }
    var oldTaskBody by remember { mutableStateOf("") }

    var selectedAlarmDataList by rememberSaveable { mutableStateOf(listOf(0, 0, 0)) }
    var selectedTimeHour by rememberSaveable { mutableIntStateOf(7) }
    var selectedTimeMinute by rememberSaveable { mutableIntStateOf(0) }
    val openDialogDate = remember { mutableStateOf(false) }
    val openDialogTime = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val taskColor = when (selectedColor) {
        2 -> {
            MaterialTheme.colorScheme.onSecondary
        }

        3 -> {
            MaterialTheme.colorScheme.error
        }

        else -> {
            MaterialTheme.colorScheme.onPrimary
        }
    }
    val nameColor = when (selectedColor) {
        2 -> {
            "متوسط"
        }

        3 -> {
            "مهم"
        }

        else -> {
            "عادی"
        }
    }

    LaunchedEffect(key1 = id) {
        if (id != 0) {
            taskViewModel.getSingleTaskById(id).collectLatest { taskItem ->
                taskTitle = taskItem.title
                oldTaskTitle = taskItem.title
                selectedColor = taskItem.taskColor
                oldSubTask.addAll(taskItem.subTask)
                subTask.addAll(taskItem.subTask)
                taskBody = taskItem.body
                oldTaskBody = taskItem.body
                createTime = taskItem.createTime
                completedTime = taskItem.completedTime
                enableAlarm = System.currentTimeMillis() < taskItem.triggerAlarmTime
                selectedAlarmDataList =
                    TaskHelper.convertMillisToDateList(taskItem.triggerAlarmTime)

                val time = TaskHelper.convertMillisToTimeList(taskItem.triggerAlarmTime)
                selectedTimeHour = time[0]
                selectedTimeMinute = time[1]
            }
        }

    }

    BackHandler(enabled = oldTaskTitle != taskTitle || oldTaskBody != taskBody || subTask.toList() != oldSubTask.toList()) {
        showSheetDiscard = true
    }
    SheetSaveDiscard(
        show = showSheetDiscard,
        onDismissRequest = { showSheetDiscard = false },
        exit = {
            showSheetDiscard = false
            focusManager.clearFocus()
            navHostController.navigateUp()
        }
    )
    DialogDeleteItemTask(
        show = showDialogDeletedSubTask,
        title = "حذف وظیفه",
        body = "آیا از حذف کردن این وظیفه اطمینان دارید؟",
        onBack = {
            showDialogDeletedSubTask = false
        },
        onDeleteItem = {
            subTask.removeAt(subTaskId)
            showDialogDeletedSubTask = false
        }
    )
    BottomUpdateSheetTask(
        title = subTaskItem.title,
        obClick = { newTitle ->
            subTask[subTaskId] = Task(title = newTitle)
        }
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BottomSheetAddTask { title ->
        if (title.isEmpty()) {
            Toast.makeText(context, "عنوان وظیفه را مشخص کنید", Toast.LENGTH_SHORT).show()
        } else {
            subTask.add(Task(title = title))
            showBottomSheetAddTask.value = false
            focusManager.clearFocus()
        }

    }


    BottomSheetSelectedColor(
        title = "لطفا اولویت وظیفه را انتخاب کنید",
        onClick = { colorIndex -> selectedColor = colorIndex })
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
            onSelectDay = { //it:JalaliCalendar

            },
            onConfirm = {
                val date = TaskHelper.jalaliToGregorian(it.year, it.month, it.day)
                selectedAlarmDataList = splitWholeDate(date)
            },
            fontFamily = font_bold
        )
    }

    Scaffold(
        snackbarHost = {
            MySnackbarHost(snackBarHostState) { data ->
                IconButton(onClick = { data.dismiss() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.background,
                    )
                }
            }
        },
        topBar = {
            TopBar(title = if (id == 0) "افزودن وظیفه گروهی" else "ویرایش وظیفه گروهی") {
                if (oldTaskTitle != taskTitle || oldTaskBody != taskBody || subTask.toList() != oldSubTask.toList()) {
                    showSheetDiscard = true
                } else {
                    focusManager.clearFocus()
                    navHostController.navigateUp()
                }
            }
        },
        bottomBar = {
            Bottom(onUpsertItem = {
                focusManager.clearFocus()
                if (taskTitle.isEmpty()) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            "عنوان وظیفه را مشخص کنید",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else if (subTask.isEmpty()) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            "حداقل یک وظیفه وارد کنید",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    if (enableAlarm && !subTask.all { it.isCompleted }) {
                        if (selectedAlarmDataList == listOf(0, 0, 0)) {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "تاریخ یادآور انتخاب نشده است",
                                    withDismissAction = true
                                )
                            }
                        } else {
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
                                    snackBarHostState.showSnackbar(
                                        message = "زمان یادآور باید از آینده باشد!",
                                        withDismissAction = true
                                    )
                                }
                            } else {
                                if (id != 0) {
                                    Log.i("1515", "update alarm")
                                    alarmViewModel.canselNotificationAlarm(
                                        context = context,
                                        id = id
                                    )
                                    alarmViewModel.setNotificationAlarm(
                                        triggerTime = triggerTime,
                                        id = id,
                                        title = taskTitle,
                                        context = context
                                    )
                                } else {
                                    Log.i("1515", "set alarm")
                                    alarmViewModel.setNotificationAlarm(
                                        triggerTime = triggerTime,
                                        id = lastId + 1,
                                        title = taskTitle,
                                        context = context
                                    )
                                }

                                val taskItem = TaskItem(
                                    id = if (id != 0) id else lastId + 1,
                                    title = taskTitle,
                                    subTask = subTask,
                                    body = taskBody,
                                    taskColor = selectedColor,
                                    createTime = if (id == 0) "${dates.hour}:${dates.min} -- ${dates.year}/${dates.month}/${dates.day}" else createTime,
                                    completedTime = if (subTask.all { it.isCompleted }) "${dates.hour}:${dates.min} -- ${dates.year}/${dates.month}/${dates.day}" else "",
                                    triggerAlarmTime = triggerTime
                                )
                                taskViewModel.upsertTask(taskItem)
                                navHostController.navigateUp()
                            }
                        }

                    } else {
                        if (id != 0) {
                            alarmViewModel.canselNotificationAlarm(
                                context = context,
                                id = id
                            )
                        }
                        val taskItem = TaskItem(
                            id = if (id != 0) id else lastId + 1,
                            title = taskTitle,
                            subTask = subTask,
                            body = taskBody,
                            taskColor = selectedColor,
                            createTime = if (id == 0) "${dates.hour}:${dates.min} -- ${dates.year}/${dates.month}/${dates.day}" else createTime,
                            completedTime = if (subTask.all { it.isCompleted }) "${dates.hour}:${dates.min} -- ${dates.year}/${dates.month}/${dates.day}" else "",
                        )
                        taskViewModel.upsertTask(taskItem)
                        navHostController.navigateUp()
                    }
                }
            },
                onBack = {
                    focusManager.clearFocus()
                    if (oldTaskTitle != taskTitle || oldTaskBody != taskBody || subTask.toList() != oldSubTask.toList()) {
                        showSheetDiscard = true
                    } else {
                        navHostController.navigateUp()
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        cursorColor = Color(0xFF2196F3)
                    ),
                    maxLines = 2,
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            text = "عنوان وظیفه ...",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    value = taskTitle,
                    onValueChange = { title ->
                        taskTitle = title
                    },
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(0.5f)
                )
            }
            item {
                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Notes,
                            contentDescription = "",
                            tint = if (taskBody.isEmpty()) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        cursorColor = Color(0xFF2196F3)
                    ),
                    maxLines = 3,
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            text = "توضیحات ...",
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = taskBody,
                    onValueChange = { body -> taskBody = body },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Start
                    )
                )
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(0.5f)
                )
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    onClick = { showBottomSheetSelectedColor.value = true })
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PriorityHigh,
                                contentDescription = "",
                                Modifier.padding(end = 8.dp),
                                tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                            )
                            Text(
                                text = "اولویت",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.scrim
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = nameColor,
                                style = MaterialTheme.typography.bodyLarge,
                                color = taskColor
                            )
                            Box(
                                modifier = Modifier
                                    .size(13.dp, 28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(taskColor)
                                    .padding(4.dp)
                            )
                            Icon(
                                modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = ""
                            )
                        }


                    }


                }
            }
            item {
                val formattedMinute = String.format(Locale.US, "%02d", selectedTimeMinute)
                SetAlarmSection(
                    enableAlarm = enableAlarm,
                    onSelectedDate = { openDialogDate.value = true },
                    onSelectedTime = { openDialogTime.value = true },
                    onEnable = { enb -> enableAlarm = enb },
                    times = "${selectedTimeHour}:${formattedMinute}",
                    dates = TaskHelper.gregorianToJalali(
                        selectedAlarmDataList[0],
                        selectedAlarmDataList[1],
                        selectedAlarmDataList[2]
                    ),
                )
            }
            item { DetailTaskSection(subTask.size) }

            if (subTask.isEmpty()) {
                item {
                    Text(
                        text = "هیچ وظیفه ای اضافه نکرده اید",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            itemsIndexed(items = subTask, key = { index, item -> index }) { index, item ->
                SwipeToDismissBoxLayout(
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = !item.isCompleted,
                    startToEnd = {
                        subTaskId = index
                        subTaskItem = item
                        showBottomUpdateSheetTask.value = true
                    },
                    endToStart = {
                        subTaskId = index
                        showDialogDeletedSubTask = true
                    }
                ) {
                    SubTaskItem(
                        item = item,
                        onCompeted = { competed ->
                            subTask[index] = item.copy(isCompleted = competed)
                        },
                        onClick = {
                            subTaskId = index
                            subTaskItem = item
                            showBottomUpdateSheetTask.value = true
                        },
                        onLongClick = {
                            subTaskId = index
                            showDialogDeletedSubTask = true
                        })
                }

            }
            item {
                OutlinedButton(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.scrim
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    onClick = { showBottomSheetAddTask.value = true }) {
                    Text(
                        text = "افزودن وظیفه",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }

    }
}


@Composable
private fun Bottom(
    onUpsertItem: () -> Unit,
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .weight(0.6f)
                .padding(horizontal = 4.dp),
            onClick = { onUpsertItem() }) {
            Text(
                text = "ذخیره",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        OutlinedButton(
            modifier = Modifier
                .weight(0.4f)
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(12.dp),

            border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.scrim
            ),
            onClick = { onBack() }) {
            Text(
                text = "لفو",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}