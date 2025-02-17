package ir.lrn.kara.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseProgress(
    modifier: Modifier,
    progress: Float,
    maxSize: Int,
    progressColor: Color=Color(0xff3B3B3B),
    enableDot:Boolean=true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
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
                    .background(color =progressColor)
            )

            if (maxSize <= 10&&enableDot) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val stepSpacing = size.width / maxSize

                    for (i in 1 until maxSize) {
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