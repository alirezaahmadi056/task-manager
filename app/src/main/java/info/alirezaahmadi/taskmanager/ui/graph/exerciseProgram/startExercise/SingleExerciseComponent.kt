package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
import info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.ExerciseItemCardBottomInfo
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import info.alirezaahmadi.taskmanager.util.getOrdinalInPersian

@Composable
fun SingleExerciseComponent(
    index: Int,
    currentExercise: ExerciseProgramItem?,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StartExerciseTopBar(
                image = currentExercise?.imageUri ?: "",
                video = currentExercise?.videoUri ?: "",
                onBack = onBack,
                openDialogListExercise = {}
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "حرکت ${getOrdinalInPersian(index + 1)} ",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 21.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = currentExercise?.name ?: "",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.description_exercise),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = currentExercise?.description?:"",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                val repetitionSetText = if(currentExercise?.dropdown==false)
                    "تا ناتوانی"
                    else "ست ${currentExercise?.repetitionSetNumber.toString().byLocate()} تایی"
                ExerciseItemCardBottomInfo(
                    icon = R.drawable.b,
                    text = "${
                        currentExercise?.setNumber.toString().byLocate()
                    } $repetitionSetText",
                    true
                )
                Spacer(Modifier.width(15.dp))
                ExerciseItemCardBottomInfo(
                    icon = R.drawable.stopwatch_progress,
                    text = "زمان تقریبی: ${currentExercise?.time.toString().byLocate()} دقیقه",
                    true
                )

            }

        }
    }
}