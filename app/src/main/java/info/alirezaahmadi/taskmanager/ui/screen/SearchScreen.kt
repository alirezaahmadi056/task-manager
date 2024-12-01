package info.alirezaahmadi.taskmanager.ui.screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.screen.notes.NotesListItem
import info.alirezaahmadi.taskmanager.ui.screen.task.TaskItemCard
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import info.alirezaahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        taskViewModel.getTasks()
    }
    val taskItems by taskViewModel.allNormalTask.collectAsState(initial = emptyList())
    val noteItems by notesViewModel.allNotesItem.collectAsState(initial = emptyList())

    var searchText by remember { mutableStateOf("") }
    var filteredTasks by remember(key1 = taskItems) { mutableStateOf(taskItems) }
    var filteredNotes by remember(key1 =noteItems ) { mutableStateOf(noteItems) }
    LaunchedEffect(searchText) {
        filteredTasks = taskItems.filter {
            it.title.contains(searchText, ignoreCase = true) || it.body.contains(searchText, ignoreCase = true)
        }

        filteredNotes = noteItems.filter {
            it.title.contains(searchText, ignoreCase = true) || it.body.contains(searchText, ignoreCase = true)
        }
    }
    SearchScreenContent(
        searchText = searchText,
        onSearchTextChange = { searchText = it },
        filteredTasks = filteredTasks,
        filteredNotes = filteredNotes,
        navHostController = navHostController,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreenContent(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    filteredTasks: List<TaskItem>,
    filteredNotes: List<NotesItem>,
    navHostController: NavHostController,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Scaffold(
        topBar = {
            OutlinedTextField(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .focusRequester(focusRequester),
                value = searchText,
                onValueChange = onSearchTextChange,
                placeholder = {
                    Text(
                        "لطفاً یک عبارت جستجو وارد کنید",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                },
                leadingIcon = { IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    } },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    cursorColor = Color(0xFF2196F3)
                ),
                textStyle = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Start),
                maxLines = 1
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (filteredNotes.isEmpty() && filteredTasks.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.emptylist),
                            contentDescription = ""
                        )
                        Text(
                            text = "آیتمی یافت نشد",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }
                }
            }
            if (filteredNotes.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = "یادداشت‌ها",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(vertical = 15.dp, horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.scrim,
                        textAlign = TextAlign.Center
                    )
                }
                items(filteredNotes) { note ->
                    NotesListItem(item = note, onClick = {
                        navHostController.navigate(Screen.AddNotesScreen.route + "?id=${note.id}")
                    })
                }
            }
            if (filteredTasks.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = "وظایف",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(vertical = 15.dp, horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.scrim,
                        textAlign = TextAlign.Center
                    )
                }
                items(filteredTasks) { task ->
                    TaskItemCard(
                        item = task,
                        onClick = {
                            navHostController.navigate(
                                Screen.AddTaskScreen.route + "?id=${task.id}"
                            )
                        },
                    )
                }
            }
        }
    }
}



