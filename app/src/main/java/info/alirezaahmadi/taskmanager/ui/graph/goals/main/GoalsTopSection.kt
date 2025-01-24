package info.alirezaahmadi.taskmanager.ui.graph.goals.main

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.BaseProgress
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import info.alirezaahmadi.taskmanager.util.getGoalColor
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel
import kotlin.math.roundToInt

@Composable
fun GoalsTopSection(
    navHostController: NavHostController,
    goalsViewModel: GoalsViewModel
) {
    val shortTermGoals by goalsViewModel.shortTermGoals.collectAsState()
    val mediumTermGoals by goalsViewModel.mediumTermGoals.collectAsState()
    val longTermGoals by goalsViewModel.longTermGoals.collectAsState()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        item { Spacer(Modifier.width(12.dp)) }
        item {
            GoalsSectionItemCard(
                goalsList = shortTermGoals,
                timeFrame = GoalsTimeFrame.SHORT
            ){
                navHostController.navigate(Screen.GoalsFullScreen(0))
            }
        }
        item {
            GoalsSectionItemCard(
                goalsList = mediumTermGoals,
                timeFrame = GoalsTimeFrame.MEDIUM
            ){
                navHostController.navigate(Screen.GoalsFullScreen(1))
            }
        }
        item {
            GoalsSectionItemCard(
                goalsList = longTermGoals,
                timeFrame = GoalsTimeFrame.LONG
            ){
                navHostController.navigate(Screen.GoalsFullScreen(2))
            }
        }

    }
}

@Composable
private fun GoalsSectionItemCard(
    goalsList: List<GoalsItem>,
    timeFrame: GoalsTimeFrame,
    onClick: () -> Unit = {}
) {
    val backGroundColor: Pair<Color, Color> =
        remember(key1 = goalsList, key2 = timeFrame) { getGoalColor(timeFrame.name) }

    val completedGoals= goalsList.count { it.isCompleted }
    val totalGoals= goalsList.size
    val pendingGoals = totalGoals - completedGoals

    val animatedProgress = animateFloatAsState(
        targetValue = if (totalGoals > 0) {
            completedGoals.toFloat() / totalGoals
        } else { 0f },
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing), label = ""
    )


    Column(
        modifier = Modifier
            .width(165.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(colors = backGroundColor.toList()))
            .clickable(onClick=onClick)
            .padding(vertical = 12.dp, horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = timeFrame.perName,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "${goalsList.size.toString().byLocate()} ${stringResource(R.string.goals)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 6.dp),
            color = Color.White
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "${(animatedProgress.value *100).roundToInt().toString().byLocate()} درصد پیشرفت",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.White
        )
        BaseProgress(
            progress = animatedProgress.value,
            maxSize = goalsList.size,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(10.dp),
            progressColor = Color(0xffEC8722)
        )
        Spacer(Modifier.height(8.dp))
    }
}