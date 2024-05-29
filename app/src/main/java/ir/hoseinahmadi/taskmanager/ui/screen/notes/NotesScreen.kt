package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun NotesScreen(
    navHostController: NavHostController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    var gridItem by remember {
        mutableStateOf(Constants.GRIDLIST)
    }

    var notesItem by remember {
        mutableStateOf<List<NotesItem>>(emptyList())
    }

    LaunchedEffect(key1 = true) {
        notesViewModel.allNotesItem.collectLatest {
            notesItem = it
        }
    }
    AlertDialogSelectedGridList(gridList = {
        gridItem =it
    })

    val lazyState = rememberLazyStaggeredGridState()
    Scaffold(
        floatingActionButton = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    expanded = lazyState.canScrollForward,
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
            }

        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        AnimatedVisibility(
            visible = gridItem,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {

            LazyVerticalStaggeredGrid(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 4.dp),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(3.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp
            ) {
                items(notesItem) { item ->
                    NotesItemCard(navHostController, item = item)
                }
            }
        }
        AnimatedVisibility(
            visible = !gridItem,
            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(notesItem) { item ->
                    NotesListItem(navHostController, item)
                }
            }
        }

    }

}
