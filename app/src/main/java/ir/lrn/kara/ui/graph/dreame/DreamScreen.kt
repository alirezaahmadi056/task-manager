package ir.lrn.kara.ui.graph.dreame

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.CenterBackTopBar
import ir.lrn.kara.ui.component.DialogDeleteItemTask
import ir.lrn.kara.viewModel.DreamViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DreamScreen(
    navHostController: NavHostController,
    dreamViewModel: DreamViewModel
) {
    val allDream by dreamViewModel.allDream.collectAsState()

    val (dreamInCompleted, dreamCompleted) = remember(
        key1 = allDream
    ) {
        allDream.reversed().partition { !it.isCompleted }
    }

    var singleId by remember { mutableStateOf<Int?>(null) }
    var isExpanded by remember { mutableStateOf(true) }
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "")
    DialogDeleteItemTask(
        title = "حذف رویا",
        body = "از حذف این رویا اطمینان دارید؟",
        onBack = { singleId = null },
        show = singleId != null,
        onDeleteItem = {
            singleId?.let { dreamViewModel.deleteDreamByID(it) }
            singleId = null
        }
    )
    Scaffold(
        topBar = {
            CenterBackTopBar(text = stringResource(R.string.my_dream)) {
                navHostController.navigateUp()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
                expanded = true,
                text = {
                    Text(
                        text = "رویای جدید",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                icon = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                    )
                },
                onClick = { navHostController.navigate(Screen.AddDreamsScreen()) }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        AnimatedContent(
            targetState = allDream.isNotEmpty(), label = ""
        ) { dreams ->
            if (dreams) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(dreamInCompleted, key = { "inCompleted:${it.id}" }) { dream ->
                        DreamItemCard(
                            item = dream,
                            onDeleted = {
                                singleId = dream.id
                            },
                            onEdit = { navHostController.navigate(Screen.DreamDetailScreen(id = dream.id)) }
                        )
                    }
                    if (dreamCompleted.isNotEmpty()) {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .clickable { isExpanded = !isExpanded }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "رویاهای محقق شده",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Toggle List",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .rotate(rotationAngle),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                    items(dreamCompleted, key = { "completed:${it.id}" }) { dream ->
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                            exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                        ) {
                            DreamItemCard(
                                item = dream,
                                onDeleted = { singleId = dream.id },
                                onEdit = {}
                            )
                        }

                    }
            }
        } else {
        DreamEmpty(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
    }

}

}


@Composable
private fun DreamEmpty(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.EmojiPeople,
            contentDescription = "",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "هیج رویایی ثبت نشده است!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}