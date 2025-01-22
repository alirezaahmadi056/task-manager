package info.alirezaahmadi.taskmanager.ui.graph.goals.fullScreen

import android.media.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Index
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.ui.component.BaseProgress
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import info.alirezaahmadi.taskmanager.util.applyQuizGraphics
import info.alirezaahmadi.taskmanager.util.getGoalColor
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun GoalsHeaderPager(
    allGoalsList: List<GoalsItem>,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val currentTimeFrame: GoalsTimeFrame = remember(page) {
                when (page) {
                    0 -> GoalsTimeFrame.SHORT
                    1 -> GoalsTimeFrame.MEDIUM
                    2 -> GoalsTimeFrame.LONG
                    else -> GoalsTimeFrame.SHORT
                }
            }
            val currentGoals = remember(
                key1 = page,
                key2 = currentTimeFrame
            ) { allGoalsList.filter { it.timeFrame == currentTimeFrame.name } }
            HeadersImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .applyQuizGraphics(pagerState, page),
                timeFrame = currentTimeFrame,
                currentList = currentGoals,
                currentIndex = page,
                onClick = { nextPage ->
                    scope.launch {
                        pagerState.animateScrollToPage(
                            nextPage,
                            animationSpec = tween(600)
                        )
                    }
                }
            )
        }
    }


@Composable
private fun HeadersImage(
    modifier: Modifier,
    timeFrame: GoalsTimeFrame,
    currentList: List<GoalsItem>,
    currentIndex: Int,
    onClick: (Int) -> Unit,
) {
    val backGroundColor: Pair<Color, Color> =
        remember(key1 = timeFrame.name) { getGoalColor(timeFrame.name) }
    val animatedProgress = animateFloatAsState(
        targetValue = (if (currentList.isNotEmpty()) {
            val completedCount = currentList.count { it.isCompleted }
            (completedCount * 100) / currentList.size
        } else {
            0
        }).toFloat(),
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing), label = ""
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.linearGradient(colors = backGroundColor.toList()))
                .padding(bottom = 12.dp, top = 12.dp, start = 30.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = timeFrame.perName,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 7.dp)
            )

            Text(
                text = "${
                    currentList.size.toString().byLocate()
                } ${stringResource(R.string.goals)}",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 6.dp, start = 4.dp),
                color = Color.White,
            )
            Spacer(Modifier.height(35.dp))
            Text(
                text = "${animatedProgress.value.roundToInt().toString().byLocate()} درصد پیشرفت",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.White
            )
            BaseProgress(
                progress = animatedProgress.value,
                maxSize = currentList.size,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(13.dp),
                progressColor = Color(0xffEC8722)
            )
            Spacer(Modifier.height(8.dp))
        }
        NextClick(
            icon = Icons.Rounded.ArrowBack,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(
                    onClick = { onClick(currentIndex.plus(1)) },
                    interactionSource = null, indication = null
                ),
            visibility = currentIndex != 2
        )
        NextClick(
            icon = Icons.Rounded.ArrowForward,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    onClick = { onClick(currentIndex.minus(1)) },
                    interactionSource = null, indication = null
                ),
            visibility = currentIndex != 0
        )

    }

}

@Composable
private fun NextClick(
    modifier: Modifier,
    icon: ImageVector,
    visibility: Boolean,
) {
    if (visibility) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.padding(7.dp)
            )
        }
    }

}