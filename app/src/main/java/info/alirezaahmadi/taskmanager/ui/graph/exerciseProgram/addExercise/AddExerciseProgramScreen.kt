package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.addExercise

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.viewModel.ExerciseProgramViewModel

@Composable
fun AddExerciseProgramScreen(
    navHostController: NavHostController,
    exerciseProgramViewModel: ExerciseProgramViewModel = hiltViewModel()
) {
    val currentDayStatus = remember { mutableStateListOf<String>() }
    var checkInput by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var selectedVideo by remember { mutableStateOf<Uri?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xff9747FF).copy(0.4f),
        topBar = { AddExerciseTopBar { navHostController.navigateUp() } },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(0.8f),
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 8.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff9747FF),
                        contentColor = Color.White
                    ),
                    onClick = {
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            SelectedExerciseDayStatusSection(
                currentSList = currentDayStatus,
                onAddDay = { currentDayStatus.add(it) },
                onRemoveDay = { currentDayStatus.remove(it) }
            )
            AnimatedVisibility(
                checkInput && currentDayStatus.isEmpty()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    text = "حداقل یک روز هفته را انتخاب کنید",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            AddImageSection(
                uri = selectedImage,
                onImageSelected = { selectedImage = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            AddVideoSection(
                uri = selectedVideo,
                onVideoSelected = {selectedVideo =it}
            )
        }
    }
}