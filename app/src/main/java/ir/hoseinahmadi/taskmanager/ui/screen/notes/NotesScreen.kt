package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.util.Constants
import ir.hoseinahmadi.taskmanager.viewModel.NotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navHostController: NavHostController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    var gridItem by remember { mutableStateOf(Constants.GRIDLIST) }
    var notesItem by remember { mutableStateOf<List<NotesItem>>(emptyList()) }
    var sortOrder by remember { mutableIntStateOf(0) }

    val sortedNotesItem = when (sortOrder) {
        1 -> notesItem.sortedBy { it.taskColor } // اولویت کم
        2 -> notesItem.sortedByDescending { it.taskColor == 2 } // اولویت معمولی
        3 -> notesItem.sortedByDescending { it.taskColor } // اولویت زیاد
        else -> notesItem.reversed() //  حالت پیش ‌فرض بر اساس اخرین یادداشت
    }
    LaunchedEffect(key1 = true) {
        notesViewModel.allNotesItem.collectLatest {
            notesItem = it
        }
    }

    var extanded by remember {
        mutableStateOf(false)
    }

    val lazyStateStagger = rememberLazyStaggeredGridState()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    AlertDialogSelectedGridList(gridList = {
        gridItem =it
    })
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { sortOrder = 1 }) {
                    Text("اولویت کم",
                        style = MaterialTheme.typography.bodySmall
                        )
                }
                Button(onClick = { sortOrder = 2 }) {
                    Text("اولویت معمولی",
                        style = MaterialTheme.typography.bodySmall

                    )
                }
                Button(onClick = { sortOrder = 3 }) {
                    Text("اولویت زیاد",
                        style = MaterialTheme.typography.bodySmall

                    )
                }
            }
        },
        floatingActionButton = {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    expanded = extanded,
                    text = {
                        Text(
                            text = "یادداشت",
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
                    onClick = { navHostController.navigate(Screen.AddNotesScreen.route) })

        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        AnimatedVisibility(
            visible = gridItem,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {

            extanded = (lazyStateStagger.firstVisibleItemScrollOffset==0||lazyStateStagger.canScrollForward)

            LazyVerticalStaggeredGrid(
                state = lazyStateStagger,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 4.dp),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(3.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp
            ) {
                items(sortedNotesItem) { item ->
                    NotesItemCard(navHostController, item = item)
                }
            }
        }
        AnimatedVisibility(
            visible = !gridItem,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {
            extanded = (lazyListState.firstVisibleItemScrollOffset==0||lazyListState.canScrollForward)

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(sortedNotesItem) { item ->
                    NotesListItem(navHostController, item)
                }
            }
        }

    }

}
