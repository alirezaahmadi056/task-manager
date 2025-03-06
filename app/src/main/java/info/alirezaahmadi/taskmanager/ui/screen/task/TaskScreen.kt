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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.task.Task
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskItemType
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.EmptyList
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.ui.component.PageType
import info.alirezaahmadi.taskmanager.ui.component.SelectedSortNotList
import info.alirezaahmadi.taskmanager.ui.component.SwipeToDismissBoxLayout
import info.alirezaahmadi.taskmanager.ui.screen.task.addTask.FastNoteSection
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.viewModel.AlarmViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn( ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        taskViewModel.getTasks()
    }
    val normalItem by taskViewModel.allNormalTask.collectAsState(initial = emptyList())
    val fastItem by taskViewModel.allFastTask.collectAsState(initial = emptyList())
    var sortOrder by remember { mutableIntStateOf(Constants.SORT_TASK) }

    // مرتب‌سازی آیتم‌ها بر اساس sortOrder
    val sortedNotesItem = remember(key1 = sortOrder, key2 = normalItem) {
        when (sortOrder) {
            1 -> normalItem.sortedBy { it.taskColor } // اولویت کم
            2 -> normalItem.sortedByDescending { it.taskColor == 2 } // اولویت معمولی
            3 -> normalItem.sortedByDescending { it.taskColor } // اولویت زیاد
            else -> normalItem.reversed() //  حالت پیش ‌فرض بر اساس اخرین یادداشت
        }
    }

    val (completedTasks, incompleteTasks) = remember(key1 =sortedNotesItem ) {
        sortedNotesItem.partition { sort -> sort.subTask.all { it.isCompleted } }
    }
    val (fastTaskCompleted, fastTaskInCompleted) = remember(key1 = fastItem) {
        fastItem.reversed().partition { it.subTask[0].isCompleted }
    }
    SelectedSortNotList(
        pageType = PageType.TASK,
        taskSort = { selectedSort ->
            sortOrder = selectedSort
        }, noteSort = {}, routineSort = {}
    )
    var singleDeleteTask by remember { mutableStateOf(TaskItem()) }
    var showDialogDelete by remember { mutableStateOf(false) }
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

    val lazyListState = rememberLazyListState()
    var expandedList by rememberSaveable { mutableStateOf(false) }
    val rotateState by animateFloatAsState(
        targetValue = if (expandedList) 180f else 0f,
        label = ""
    )
    var hasNavigated by remember { mutableStateOf(false) }
    LaunchedEffect(fastItem) {
        lazyListState.animateScrollToItem(0)
    }
    val expanded by remember(key1 =lazyListState ) { derivedStateOf { lazyListState.canScrollForward || lazyListState.firstVisibleItemIndex == 0 } }
    Scaffold(
        snackbarHost = {
            MySnackbarHost(
                snackBarHostState,
                action = { data ->
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
                    val lastId = getNextTaskId(normalTasks = normalItem, fastTasks = fastItem)
                    navHostController.navigate(Screen.AddTaskScreen.route + "?lastId=${lastId}")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        bottomBar = {
            FastNoteSection(
                taskViewModel = taskViewModel,
                id = getNextTaskId(normalTasks = normalItem, fastTasks = fastItem)

            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            if (normalItem.isNotEmpty() || fastItem.isNotEmpty()) {
                items(fastTaskInCompleted, key = { it.id }) { task ->
                    SwipeToDismissBoxLayout(
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                        startToEnd = {
                            if (!hasNavigated) {
                                hasNavigated = true
                                navHostController.navigate(Screen.AddTaskScreen.route + "?id=${task.id}")
                            }
                        },
                        endToStart = {
                            singleDeleteTask = task
                            showDialogDelete = true
                        }
                    ) {
                        FastTaskItemCard(task,
                            onIsCompletedClick = {
                                taskViewModel.upsertTask(
                                    TaskItem(
                                        id = task.id, title = task.title,
                                        type = TaskItemType.FAST.name,
                                        subTask = listOf(
                                            Task(
                                                title = task.subTask[0].title,
                                                isCompleted = it
                                            ),
                                        ),
                                    )
                                )
                            },
                            onLongClick = {
                                singleDeleteTask = task
                                showDialogDelete = true
                            })
                    }
                }
                items(incompleteTasks, key = { task -> task.id }) { taskItem ->
                    SwipeToDismissBoxLayout(
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = true,
                        startToEnd = {
                            if (!hasNavigated) {
                                hasNavigated = true
                                navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}")
                            }
                        },
                        endToStart = {
                            singleDeleteTask = taskItem
                            showDialogDelete = true
                        }
                    ) {
                        TaskItemCard(
                            item = taskItem,
                            onClick = { navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}") },
                            onLogClick = {
                                singleDeleteTask = taskItem
                                showDialogDelete = true
                            }
                        )
                    }
                }
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(12.dp),
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
                                text = "( ${TaskHelper.taskByLocate((completedTasks.size + fastTaskCompleted.size).toString())} ${"وظیفه )"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.scrim,
                            )

                        }


                        IconButton(onClick = { expandedList = !expandedList }) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier
                                    .rotate(rotateState),
                                tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                            )
                        }
                    }
                }
                items(fastTaskCompleted, key = { it.id }) { task ->
                    AnimatedVisibility(
                        visible = expandedList,
                        enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                        exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                    ) {
                        SwipeToDismissBoxLayout(
                            enableDismissFromEndToStart = true,
                            enableDismissFromStartToEnd = false,
                            startToEnd = {
                                if (!hasNavigated) {
                                    hasNavigated = true
                                    navHostController.navigate(Screen.AddTaskScreen.route + "?id=${task.id}")
                                }
                            },
                            endToStart = {
                                singleDeleteTask = task
                                showDialogDelete = true
                            }
                        ) {
                            FastTaskItemCard(
                                taskItem = task,
                                onIsCompletedClick = {
                                    taskViewModel.upsertTask(
                                        TaskItem(
                                            id = task.id, title = task.title,
                                            type = TaskItemType.FAST.name,
                                            subTask = listOf(
                                                Task(
                                                    title = task.subTask[0].title,
                                                    isCompleted = it
                                                ),
                                            ),
                                        )
                                    )
                                }, onLongClick = {
                                    singleDeleteTask = task
                                    showDialogDelete = true
                                },
                                isCompleted = true
                            )
                        }
                    }
                }
                items(completedTasks, key = { task -> task.id }) { taskItem ->
                    AnimatedVisibility(
                        visible = expandedList,
                        enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                        exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                    ) {
                        SwipeToDismissBoxLayout(
                            enableDismissFromEndToStart = true,
                            enableDismissFromStartToEnd = true,
                            startToEnd = {
                                navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}")
                            },
                            endToStart = {
                                singleDeleteTask = taskItem
                                showDialogDelete = true
                            }
                        ){
                            TaskItemCard(
                                item = taskItem,
                                onClick = { navHostController.navigate(Screen.AddTaskScreen.route + "?id=${taskItem.id}") },
                                onLogClick = {
                                    singleDeleteTask = taskItem
                                    showDialogDelete = true
                                }
                            )
                        }
                    }
                }
            } else {
                item { EmptyList() }
            }

        }

    }

}


fun getNextTaskId(normalTasks: List<TaskItem>, fastTasks: List<TaskItem>): Int {
    val maxNormalId = normalTasks.maxOfOrNull { it.id } ?: 0
    val maxFastId = fastTasks.maxOfOrNull { it.id } ?: 0
    return maxOf(maxNormalId, maxFastId)
}

