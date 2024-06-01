package ir.hoseinahmadi.taskmanager.ui.screen.task.addTask

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.task.Task
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.ui.screen.notes.addNotes.BottomSheetSelectedColor
import ir.hoseinahmadi.taskmanager.ui.screen.notes.addNotes.showBottomSheetSelectedColor
import ir.hoseinahmadi.taskmanager.viewModel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    id: Int,
    taskViewModel: TaskViewModel = hiltViewModel()
) {

    var taskTitle by remember { mutableStateOf("") }
    var selectedColor by remember { mutableIntStateOf(1) }
    var subTask by remember { mutableStateOf<List<Task>>(mutableListOf()) }
    var taskBody by remember {
        mutableStateOf("")
    }
    var subTaskItem by remember { mutableStateOf(Task()) }
    var subTaskId by remember { mutableIntStateOf(0) }

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

    if (id != 0) {
        LaunchedEffect(key1 = id) {
            taskViewModel.getSingleTaskById(id).collectLatest { taskItem ->
                taskTitle = taskItem.title
                selectedColor = taskItem.taskColor
                subTask = taskItem.subTask
                taskBody =taskItem.body
            }
        }
    }

    BottomUpdateSheetTask(
        title = subTaskItem.title,
        obClick = { newTitle ->
            val updatedSubTasks = subTask.toMutableList().apply {
                this[subTaskId] = this[subTaskId].copy(title = newTitle)
            }
            subTask = updatedSubTasks
        }
    )

    BottomSheetAddTask { title ->
        subTask = subTask + Task(title = title)
    }

    BottomSheetSelectedColor(onClick = { colorIndex -> selectedColor = colorIndex })

    Scaffold(
        topBar = {
            Top(title = if (id == 0) "افزودن وظیفه گروهی" else "ویرایش وظیفه گروهی") {
                navHostController.popBackStack()
            }
        },
        bottomBar = {
            Bottom(onUpsertItem = {
                if (taskTitle.isEmpty()) {
                    Toast.makeText(context, "عنوان وظیفه را مشخص کنید", Toast.LENGTH_SHORT).show()
                } else if (taskTitle.length < 10){
                    Toast.makeText(context, "عنوان وظیفه بزرگ تری وارد کنید", Toast.LENGTH_SHORT).show()
                }else if (subTask.isEmpty()) {
                    Toast.makeText(context, "حداقل یک وظیفه وارد کنید", Toast.LENGTH_SHORT).show()
                }else {
                    val taskItem = TaskItem(
                        id = id,
                        title = taskTitle,
                        subTask = subTask,
                        body = taskBody,
                        taskColor = selectedColor
                    )
                    taskViewModel.upsertTask(taskItem)
                    navHostController.popBackStack()
                }
            }, onBack = {
                navHostController.popBackStack()
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
                    onValueChange = { taskTitle = it },
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray.copy(0.5f)
                )
            }

            item {

                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Notes,
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
                    maxLines =3,
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
                    onValueChange = { taskBody = it },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Start
                    )
                )
                HorizontalDivider(
                    thickness = 1.dp,
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
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray.copy(0.5f)
                )
            }
            item {
                DetailTaskSection(subTask.size)
            }

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
            itemsIndexed(subTask) { index, item ->
                SubTaskItem(item, onCompeted = { competed ->
                    val updatedSubTasks = subTask.toMutableList().apply {
                        this[index] = this[index].copy(isCompleted = competed)
                    }
                    subTask = updatedSubTasks
                }, onClick = {
                    subTaskId = index
                    subTaskItem = item
                    showBottomUpdateSheetTask.value = true
                })


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
private fun Top(title: String, onBack: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        IconButton(onClick = { onBack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "",
                Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.scrim
            )
        }
        Text(
            text = title,
            color = MaterialTheme.colorScheme.scrim,
            style = MaterialTheme.typography.bodyLarge
        )
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