package ir.lrn.kara.ui.graph.exerciseProgram.startExercise

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProgressExerciseSection(
    currentIndex: Int,
    maxSize: Int
) {
    val progress by animateFloatAsState(
        targetValue = (currentIndex.plus(1).toFloat() / maxSize.toFloat()).coerceIn(0f, 1f),
        label = "",
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(0.2f),
            text = "${(progress * 100).toInt()} درصد ",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
                    .background(color = Color(0xffD9D9D9))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(color = Color(0xff9747FF))
            )

            if(maxSize<=10){
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val stepCount = maxSize
                    val stepSpacing = size.width / stepCount

                    for (i in 1 until stepCount) {
                        val xPosition = stepSpacing * i
                        drawCircle(
                            color = if (xPosition / size.width >= (1 - progress)) Color.White else Color.Gray,
                            radius = 3.dp.toPx(),
                            center = Offset(xPosition, size.height / 2)
                        )
                    }
                }
            }

        }


    }


}