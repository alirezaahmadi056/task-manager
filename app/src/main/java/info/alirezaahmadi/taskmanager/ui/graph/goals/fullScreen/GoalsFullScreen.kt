package info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.util.getGoalColor
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsFullScreen(
    navHostController: NavHostController,
    pageIndex: Int,
    goalsViewModel: GoalsViewModel
) {
    val alaGoals = GoalsItem.fakeList
    val pagerState = rememberPagerState(initialPage = pageIndex) { 3 }
    val currentTimeFrame: GoalsTimeFrame = remember(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> GoalsTimeFrame.SHORT
            1 -> GoalsTimeFrame.MEDIUM
            2 -> GoalsTimeFrame.LONG
            else -> GoalsTimeFrame.SHORT
        }
    }
    val currentGoals = remember(key1 = alaGoals, key2 = currentTimeFrame) {
        alaGoals.filter { it.timeFrame == currentTimeFrame.name }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = { GoalsTopBar(""){navHostController.navigateUp()} }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GoalsHeaderPager(
                    pagerState = pagerState,
                    allGoalsList = alaGoals,
                )
            }
            stickyHeader { SectionAddGoals(
                color = getGoalColor(currentTimeFrame.name).second,
                onAddClick = {}
            ) }
            if (currentGoals.isEmpty()){
                item { GoalsEmpty() }
            }
            items(currentGoals){goals->
                GoalsItemCard(
                    item = goals,
                    onClick = {},
                    onLongClick = {}
                )
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