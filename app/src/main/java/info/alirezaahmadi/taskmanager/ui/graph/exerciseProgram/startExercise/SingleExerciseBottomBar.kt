package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R

@Composable
fun SingleExerciseBottomBar(
    currentIndex: Int,
    enableScroll: Boolean,
    maxSize: Int,
    onFinish: () -> Unit,
    onNext: (Int) -> Unit,
    onPrevious: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        ProgressExerciseSection(
            currentIndex = currentIndex,
            maxSize = maxSize
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = currentIndex + 1 == maxSize, label = "",
                contentAlignment = Alignment.Center
            ) { target ->
                if (target) {
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff9747FF),
                            contentColor = Color.White
                        ),
                        onClick = onFinish
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 25.dp),
                            text = stringResource(R.string.end_exercise),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    TextButton(
                        enabled = enableScroll,
                        onClick = { onNext(currentIndex.plus(1)) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xff9747FF),
                            disabledContentColor = Color(0xff9747FF).copy(0.4f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.next_exercise),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }


            TextButton(
                onClick = { onPrevious(currentIndex.minus(1)) },
                enabled = currentIndex > 0 && enableScroll,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xff9747FF),
                    disabledContentColor = Color(0xff9747FF).copy(0.4f)
                )
            ) {
                Text(
                    text = stringResource(R.string.previous_exercise),
                    style = MaterialTheme.run { typography.bodyLarge },
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = ""
                )

            }

        }
        Spacer(Modifier.navigationBarsPadding())
    }
}
