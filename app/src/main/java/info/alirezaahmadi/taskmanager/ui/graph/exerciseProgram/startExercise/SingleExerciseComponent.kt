package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.startExercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem

@Composable
fun SingleExerciseComponent(
    currentExercise: ExerciseProgramItem?,
    onBack:()->Unit,
){

    Scaffold(
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
                .padding(horizontal = 12.dp)
        ) {

        }
    }
}