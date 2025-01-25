package info.alirezaahmadi.taskmanager.ui.graph.dreame

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DreamScreen(
    navHostController: NavHostController,
    dreamViewModel: DreamViewModel
) {
    val allDream by dreamViewModel.getAllDreamItemDao().collectAsState(emptyList())

    val (dreamInCompleted, dreamCompleted) = remember(
        key1 = allDream
    ) {
        allDream.reversed().partition { !it.isCompleted }
    }
    Scaffold(
        topBar = {
            CenterBackTopBar(text = stringResource(R.string.my_dream)) {
                navHostController.navigateUp()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = Color.Black,
                expanded = true,
                text = {
                    Text(
                        text = "رویای جدید",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                onClick = { navHostController.navigate(Screen.AddDreamsScreen()) }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(dreamInCompleted, key = {"inCompleted:${it.id}"}) { dream ->
                DreamItemCard(
                    item = dream,
                    onDeleted = {},
                    onEdit = {}
                )
            }
            stickyHeader {
                Text(
                    text = "رویاهای محقق شده",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(12.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
            }
            items(dreamInCompleted, key = {"completed:${it.id}"}) { dream ->
                DreamItemCard(
                    item = dream,
                    onDeleted = {},
                    onEdit = {}
                )
            }
        }
    }

}