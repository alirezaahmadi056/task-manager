package info.alirezaahmadi.taskmanager.ui.screen.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.EmptyList
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.ui.component.SelectedSortNotList
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {

    val item by taskViewModel.allItem.collectAsState(initial = emptyList())
    var sortOrder by remember { mutableIntStateOf(Constants.SORT_TASK) }


    // مرتب‌سازی آیتم‌ها بر اساس sortOrder
    val sortedNotesItem = when (sortOrder) {
        1 -> item.sortedBy { it.taskColor } // اولویت کم
        2 -> item.sortedByDescending { it.taskColor == 2 } // اولویت معمولی
        3 -> item.sortedByDescending { it.taskColor } // اولویت زیاد
        else -> item.reversed() //  حالت پیش ‌فرض بر اساس اخرین یادداشت
    }

    val (completedTasks, incompleteTasks) = sortedNotesItem.partition { sort ->
        sort.subTask.all { it.isCompleted }
    }
    SelectedSortNotList(false, noteSort = {}, taskSort = { selectedSort ->
        sortOrder = selectedSort
    }
    )


    var singleDeleteTask by remember { mutableStateOf(TaskItem()) }
    var showDialogDelete by remember {
        mutableStateOf(false)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    DialogDeleteItemTask(
        title = "حذف وظیفه",
        body = "آیا از حذف این وظیفه اطمینان دارید؟",
        onBack = { showDialogDelete = false },
        onDeleteItem = {
            taskViewModel.deleteTask(singleDeleteTask)
              alarmViewModel.canselNotificationAlarm(
                  context = context,
                  id = singleDeleteTask.id
              )
            showDialogDelete = false
            scope.launch {
                snackBarHostState.showSnackbar(
                    "وظیفه با موفقیت حذف شد",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            }
        },
        show = showDialogDelete
    )

    var expanded by remember {
        mutableStateOf(false)
    }
    val lazyListState = rememberLazyListState()
    var expandedList by rememberSaveable {
        mutableStateOf(false)
    }
    val rotateState by animateFloatAsState(
        targetValue = if (expandedList) 180f else 0f,
        label = ""
    )
    Scaffold(
        snackbarHost = {
            MySnackbarHost(
                snackBarHostState,
                action = {data->
                    TextButton(onClick = {
                        taskViewModel.upsertTask(singleDeleteTask)
                        alarmViewModel.setNotificationAlarm(
                            context = context,
                            id = singleDeleteTask.id,
                            title = singleDeleteTask.title,
                            triggerTime = singleDeleteTask.triggerAlarmTime
                        )
                        data.dismiss()
                    }) {
                        Text(
                            "بازگردانی",
                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = expanded,
                text = {
                    Text(
                        text = "وظیفه",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.NoteAdd,
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                onClick = {
                    val lastId = if (item.isNotEmpty()) item.last().id else 0
                    navHostController.navigate(Screen.AddTaskScreen.route + "?lastId=${lastId}")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        expanded =
            (lazyListState.firstVisibleItemScrollOffset == 0 || lazyListState.canScrollForward)
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
        ) {
            if (item.isNotEmpty()) {
                items(incompleteTasks, key = { task -> task.id }) { taskItem ->
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        val swipeToDismiss = rememberSwipeToDismissBoxState(
                            confirmValueChange = { swip ->
                                when (swip) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}")
                                    }

                                    SwipeToDismissBoxValue.EndToStart -> {
                                        singleDeleteTask = taskItem
                                        showDialogDelete = true
                                    }

                                    SwipeToDismissBoxValue.Settled -> {
                                    }
                                }
                                return@rememberSwipeToDismissBoxState false
                            }
                        )
                        SwipeToDismissBox(
                            enableDismissFromEndToStart = true,
                            enableDismissFromStartToEnd = true,
                            state = swipeToDismiss,
                            backgroundContent = {
                                when (swipeToDismiss.dismissDirection) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(5.dp)
                                                .clip(RoundedCornerShape(11.dp))
                                                .background(Color(0xFF4CAF50)),
                                            contentAlignment = Alignment.CenterStart
                                        )
                                        {
                                            Icon(
                                                Icons.Rounded.EditNote,
                                                contentDescription = "",
                                                tint = Color.White,
                                                modifier = Modifier.size(50.dp)
                                            )

                                        }
                                    }

                                    SwipeToDismissBoxValue.EndToStart -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(5.dp)
                                                .clip(RoundedCornerShape(11.dp))
                                                .background(Color.Red),
                                            contentAlignment = Alignment.CenterEnd
                                        )
                                        {
                                            Icon(
                                                Icons.Rounded.DeleteSweep,
                                                contentDescription = "",
                                                tint = Color.White, modifier = Modifier.size(50.dp)
                                            )

                                        }
                                    }

                                    SwipeToDismissBoxValue.Settled -> {}
                                }

                            }
                        ) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                TaskItemCard(navHostController = navHostController, item = taskItem)
                            }
                        }
                    }
                }
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = " تکمیل شده",
                                color = MaterialTheme.colorScheme.primary,

                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "( ${TaskHelper.taskByLocate(completedTasks.size.toString())} ${"وظیفه )"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.scrim,
                            )

                        }


                        IconButton(onClick = { expandedList = !expandedList }) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "",
                                modifier = Modifier
                                    .rotate(rotateState),
                                tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                            )
                        }
                    }
                }
                items(completedTasks, key = { task -> task.id }){ taskItem ->
                    AnimatedVisibility(
                        visible = expandedList,
                        enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                        exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            val swipeToDismiss = rememberSwipeToDismissBoxState(
                                confirmValueChange = { swip ->
                                    when (swip) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}")
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            singleDeleteTask = taskItem
                                            showDialogDelete = true
                                        }

                                        SwipeToDismissBoxValue.Settled -> {
                                        }
                                    }
                                    return@rememberSwipeToDismissBoxState false
                                }
                            )
                            SwipeToDismissBox(
                                enableDismissFromEndToStart = true,
                                enableDismissFromStartToEnd = true,
                                state = swipeToDismiss,
                                backgroundContent = {
                                    when (swipeToDismiss.dismissDirection) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                                    .clip(RoundedCornerShape(11.dp))
                                                    .background(MaterialTheme.colorScheme.onPrimary),
                                                contentAlignment = Alignment.CenterStart
                                            )
                                            {
                                                Icon(
                                                    Icons.Rounded.EditNote,
                                                    contentDescription = "",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(50.dp)
                                                )

                                            }
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                                    .clip(RoundedCornerShape(11.dp))
                                                    .background(MaterialTheme.colorScheme.error),
                                                contentAlignment = Alignment.CenterEnd
                                            )
                                            {
                                                Icon(
                                                    Icons.Rounded.DeleteSweep,
                                                    contentDescription = "",
                                                    tint = Color.White, modifier = Modifier.size(50.dp)
                                                )
                                            }
                                        }

                                        SwipeToDismissBoxValue.Settled -> {}
                                    }

                                }
                            ) {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                    TaskItemCard(navHostController = navHostController, item = taskItem)
                                }
                            }
                        }
                    }
                }
            } else {
                item { EmptyList() }
            }

        }

    }

}








