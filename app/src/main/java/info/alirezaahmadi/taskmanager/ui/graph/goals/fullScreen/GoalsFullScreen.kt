package info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.util.getGoalColor
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsFullScreen(
    navHostController: NavHostController,
    pageIndex: Int,
    goalsViewModel: GoalsViewModel
) {
    val pagerState = rememberPagerState(initialPage = pageIndex) { 3 }
    val currentTimeFrame: GoalsTimeFrame = remember(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> GoalsTimeFrame.SHORT
            1 -> GoalsTimeFrame.MEDIUM
            2 -> GoalsTimeFrame.LONG
            else -> GoalsTimeFrame.SHORT
        }
    }
    val shortTermGoals by goalsViewModel.shortTermGoals.collectAsState()
    val mediumTermGoals by goalsViewModel.mediumTermGoals.collectAsState()
    val longTermGoals by goalsViewModel.longTermGoals.collectAsState()
    val allTermGoals by goalsViewModel.allTermGoals.collectAsState()
    val (goalsInCompleted, goalsCompleted) = remember(
        currentTimeFrame,
        shortTermGoals,
        mediumTermGoals,
        longTermGoals
    ) {
        when (currentTimeFrame) {
            GoalsTimeFrame.SHORT -> shortTermGoals.reversed().partition { !it.isCompleted }
            GoalsTimeFrame.MEDIUM -> mediumTermGoals.reversed().partition { !it.isCompleted }
            GoalsTimeFrame.LONG -> longTermGoals.reversed().partition { !it.isCompleted }
        }
    }
    val currentColor: Pair<Color, Color> =
        remember(key1 = currentTimeFrame) { getGoalColor(currentTimeFrame.name) }
    val lazyList = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showFloatingButton by remember { derivedStateOf { lazyList.firstVisibleItemIndex > 0 } }
    var singleId by remember { mutableStateOf<Int?>(null) }
    DialogDeleteItemTask(
        show = singleId != null,
        title = "حذف هدف",
        body = "آیا از حذف کردن هدف راضی هستید؟",
        onBack = { singleId = null },
        onDeleteItem = {
            singleId?.let { goalsViewModel.deletedGoalsById(it) }
            singleId = null
        }
    )
    Scaffold(
        containerColor = Color.White,
        topBar = { CenterBackTopBar(stringResource(R.string.my_goals)) { navHostController.navigateUp() } },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatingButton,
                enter = fadeIn(animationSpec = tween(600)),
                exit = fadeOut(animationSpec = tween(600))
            ) {
                FloatingActionButton(
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = { scope.launch { lazyList.animateScrollToItem(0) } },
                    containerColor = currentColor.second,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Start
    ) { innerPadding ->
        LazyColumn(
            state = lazyList,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GoalsHeaderPager(
                    pagerState = pagerState,
                    allGoalsList = allTermGoals,
                    color = currentColor
                )
            }
            stickyHeader {
                SectionAddGoals(
                    currentTimeFrame = if (showFloatingButton) " ( ${currentTimeFrame.perName} )" else "",
                    color = getGoalColor(currentTimeFrame.name).second,
                    onAddClick = { navHostController.navigate(Screen.AddGoalsScreen(timeFrame = currentTimeFrame.name)) },
                )
            }
            if (goalsInCompleted.isEmpty() && goalsCompleted.isEmpty()) {
                item { GoalsEmpty() }
            }
            items(items = goalsInCompleted, key = { it.id }) { goals ->
                GoalsItemCard(
                    item = goals,
                    onClick = { navHostController.navigate(Screen.GoalsDetail(goals.id)) },
                    onLongClick = { singleId = goals.id },
                    currentColor = currentColor
                )
            }
            if (goalsCompleted.isNotEmpty()) {
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 12.dp),
                        text = "اهداف تیک خورده",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
                items(items = goalsCompleted, key = { "completed${it.id}" }) { goals ->
                    GoalsItemCard(
                        item = goals,
                        onClick = {},
                        onLongClick = { singleId = goals.id },
                        currentColor = currentColor
                    )
                }
            }

        }
    }

}

@Composable
private fun GoalsEmpty() {
    val config = LocalConfiguration.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(config.screenHeightDp.dp / 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(15.dp))
        Icon(
            imageVector = Icons.Rounded.EmojiPeople,
            contentDescription = "",
            modifier = Modifier.size(100.dp),
            tint = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "هدفی برای این بازه ثبت نشده است!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black

        )
    }
}