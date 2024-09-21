package info.alirezaahmadi.taskmanager.ui.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.EmptyList
import info.alirezaahmadi.taskmanager.ui.component.SelectedSortNotList
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navHostController: NavHostController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    var gridItem by remember { mutableStateOf(Constants.GRIDLIST) }
    var notesItem by remember { mutableStateOf<List<NotesItem>>(emptyList()) }
    var sortOrder by remember { mutableIntStateOf(Constants.SORT_NOTE) }

    val sortedNotesItem = when (sortOrder) {
        1 -> notesItem.sortedBy { it.taskColor } // اولویت کم
        2 -> notesItem.sortedByDescending { it.taskColor == 2 } // اولویت معمولی
        3 -> notesItem.sortedByDescending { it.taskColor } // اولویت زیاد
        else -> notesItem.reversed() //  حالت پیش ‌فرض بر اساس اخرین یادداشت
    }
    SelectedSortNotList(true, noteSort = { selectedSort ->
        sortOrder = selectedSort
    }, taskSort = {})

    LaunchedEffect(key1 = true) {
        notesViewModel.allNotesItem.collectLatest {
            notesItem = it
        }
    }

    var extanded by remember {
        mutableStateOf(false)
    }

    var singleDeleteNotes by remember {
        mutableStateOf(NotesItem())
    }
    var showDialogDelete by remember {
        mutableStateOf(false)
    }
    val snackBarHostState = remember { SnackbarHostState() }

    val lazyStateStagger = rememberLazyStaggeredGridState()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    DialogDeleteItemTask(
        title = "حذف یادداشت",
        body = "آیا از حذف این یادداشت اطمینان دارید؟",
        onBack = {
            showDialogDelete = false
        },
        onDeleteItem = {
            notesViewModel.deleteTask(singleDeleteNotes)
            showDialogDelete = false
            scope.launch {
                snackBarHostState.showSnackbar(
                    "یادداشت با موفقیت حذف شد",
                    duration = SnackbarDuration.Short
                )
            }
        },
        show = showDialogDelete
    )
    AlertDialogSelectedGridList(gridList = {
        gridItem = it
    })
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(
                    action = {
                        TextButton(onClick = {
                            notesViewModel.upsertNotesItem(singleDeleteNotes)
                            data.dismiss()
                        }) {
                            Text(
                                "بازگردانی",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    Text(
                        data.visuals.message,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyMedium
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
            var hasNavigated by remember { mutableStateOf(false) }
            extanded =
                (lazyStateStagger.firstVisibleItemScrollOffset == 0 || lazyStateStagger.canScrollForward)
            if (sortedNotesItem.isNotEmpty()) {
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
                    items(sortedNotesItem, key = { it.id }) { notesItem ->
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            val swipeToDismiss = rememberSwipeToDismissBoxState(
                                confirmValueChange = { swip ->
                                    when (swip) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            if (!hasNavigated) {
                                                hasNavigated = true
                                                navHostController.navigate(Screen.AddNotesScreen.route + "?id=${notesItem.id}")
                                            }
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            singleDeleteNotes = notesItem
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
                                                    .clip(RoundedCornerShape(11.dp)),
                                                contentAlignment = Alignment.CenterStart
                                            )
                                            {
                                                Box(
                                                    Modifier
                                                        .clip(CircleShape)
                                                        .background(Color(0xFF4CAF50)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.Rounded.EditNote,
                                                        contentDescription = "",
                                                        tint = Color.White,
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .size(50.dp)
                                                    )
                                                }


                                            }
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                                    .clip(RoundedCornerShape(11.dp)),
                                                contentAlignment = Alignment.CenterEnd
                                            )
                                            {
                                                Box(
                                                    Modifier
                                                        .clip(CircleShape)
                                                        .background(Color.Red),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.Rounded.DeleteSweep,
                                                        contentDescription = "",
                                                        tint = Color.White,
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .size(50.dp)
                                                    )
                                                }


                                            }
                                        }

                                        SwipeToDismissBoxValue.Settled -> {}
                                    }

                                }
                            ) {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                    NotesItemCard(navHostController, item = notesItem)
                                }
                            }
                        }

                    }
                }
            } else {
                EmptyList()
            }
        }
        AnimatedVisibility(
            visible = !gridItem,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {
            var hasNavigated by remember { mutableStateOf(false) }
            extanded =
                (lazyListState.firstVisibleItemScrollOffset == 0 || lazyListState.canScrollForward)
            if (sortedNotesItem.isNotEmpty()) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    items(sortedNotesItem) { notesItem ->
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            val swipeToDismiss = rememberSwipeToDismissBoxState(
                                confirmValueChange = { swip ->
                                    when (swip) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            if (!hasNavigated) {
                                                hasNavigated = true
                                                navHostController.navigate(Screen.AddNotesScreen.route + "?id=${notesItem.id}")
                                            }
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            singleDeleteNotes = notesItem
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
                                                    tint = Color.White,
                                                    modifier = Modifier.size(50.dp)
                                                )

                                            }
                                        }

                                        SwipeToDismissBoxValue.Settled -> {}
                                    }

                                }
                            ) {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                    NotesListItem(navHostController, notesItem)
                                }
                            }
                        }

                    }
                }
            } else {
                EmptyList()
            }

        }

    }

}
