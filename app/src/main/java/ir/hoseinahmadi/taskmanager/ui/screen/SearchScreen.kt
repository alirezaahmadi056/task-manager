package ir.hoseinahmadi.taskmanager.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.R
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.ui.screen.notes.NotesListItem
import ir.hoseinahmadi.taskmanager.ui.screen.task.TaskItemCard
import ir.hoseinahmadi.taskmanager.viewModel.NotesViewModel
import ir.hoseinahmadi.taskmanager.viewModel.TaskViewModel

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val taskItems by taskViewModel.allItem.collectAsState(initial = emptyList())
    val noteItems by notesViewModel.allNotesItem.collectAsState(initial = emptyList())

    var searchText by remember { mutableStateOf("") }
    var filteredTasks by remember { mutableStateOf(taskItems) }
    var filteredNotes by remember { mutableStateOf(noteItems) }
    var searchActive by remember { mutableStateOf(false) }

    // فیلتر کردن آیتم‌ها بر اساس متن جستجو
    LaunchedEffect(searchText) {
        filteredTasks = if (searchText.isEmpty()) {
            emptyList() // برای اینکه هیچ آیتمی نمایش داده نشود
        } else {
            taskItems.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
        }

        filteredNotes = if (searchText.isEmpty()) {
            emptyList() // برای اینکه هیچ آیتمی نمایش داده نشود
        } else {
            noteItems.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
        }
    }

    // رابط کاربری صفحه جستجو
    SearchScreenContent(
        searchText = searchText,
        onSearchTextChange = { searchText = it },
        filteredTasks = filteredTasks,
        filteredNotes = filteredNotes,
        navHostController = navHostController,
        searchActive = searchActive,
        onSearchActiveChange = { searchActive = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    filteredTasks: List<TaskItem>,
    filteredNotes: List<NotesItem>,
    navHostController: NavHostController,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    }
                    Text(
                        text = "جستجو",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.LightGray.copy(0.5f)
                )

            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            SearchBar(
                query = searchText,
                onQueryChange = onSearchTextChange,
                onSearch = {  },
                active = searchActive,
                onActiveChange = onSearchActiveChange,
                placeholder = { Text("لطفاً یک عبارت جستجو وارد کنید",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim,) },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (filteredTasks.isEmpty() && filteredNotes.isEmpty()&&searchText.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(painter = painterResource(id = R.drawable.emptylist),
                            contentDescription ="" )
                        Text(
                            text = "آیتمی یافت نشد",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }

                } else
                {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (filteredTasks.isNotEmpty()) {
                            item {
                                Text(
                                    text = "وظایف",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding( 8.dp,),
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            }
                            items(filteredTasks) { task ->
                                TaskItemCard(navHostController = navHostController, item = task)
                            }
                        }
                        if (filteredNotes.isNotEmpty()) {
                            item {
                                Text(
                                    text = "یادداشت‌ها",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp),
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            }
                            items(filteredNotes) { note ->
                                NotesListItem(navHostController = navHostController, item = note)
                            }
                        }
                    }
                }
            }
        }
    }

}


