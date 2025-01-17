package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleExerciseBottomBar(
    currentIndex: Int,
    maxSize: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        ProgressExerciseSection(
            currentIndex = currentIndex,
            maxSize=maxSize
        )
        Spacer(Modifier.height(12.dp))
    }
}