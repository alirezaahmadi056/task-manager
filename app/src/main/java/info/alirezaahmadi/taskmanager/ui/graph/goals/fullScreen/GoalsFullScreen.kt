package info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel

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
        topBar = {
            GoalsHeaderPager(
                pagerState = pagerState,
                allGoalsList = alaGoals,
                onBack = { navHostController.navigateUp() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {

        }
    }

}