package info.alirezaahmadi.taskmanager.ui.graph.goals.addGoals

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddGoalsScreen(
    navHostController: NavHostController,
    id: Int,
    time:String,
    goalsViewModel: GoalsViewModel,
) {
    var selectedImage by remember { mutableStateOf<String?>(null) }
    var timeFrame by remember { mutableStateOf(time) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var checkInput by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(id) {
        goalsViewModel.getGoalsById(id).collectLatest { goalsItem ->
            goalsItem?.let { goals ->
                selectedImage = goals.imageUri
                timeFrame = goalsItem.timeFrame
                title = goals.title
                description = goals.description
                selectedDate = goals.data
            }
        }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            GoalsTopBar(text = stringResource(if (id == 0) R.string.add_goals else R.string.update_goals))
            { navHostController.navigateUp() }
        },
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
                        .fillMaxWidth(0.9f),
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 8.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff535CF0),
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (selectedDate != null && title.isNotEmpty()) {
                            checkInput = false
                            goalsViewModel.upsertGoals(
                                GoalsItem(
                                    id = id,
                                    timeFrame = timeFrame,
                                    title = title,
                                    description = description,
                                    imageUri = (selectedImage ?: "").toString(),
                                    data = selectedDate ?: "",
                                    isCompleted = false
                                )
                            )
                            navHostController.navigateUp()
                        } else {
                            checkInput = true
                        }
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
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            SectionSelectedGoalsTimFrame(
                currentTimeFrame = timeFrame,
                onSelectedTimeFrame = { timeFrame = it }
            )
            AddImageGoalsSection(
                uri =Uri.parse(selectedImage?:"") ,
                onImageSelected = { selectedImage = it }
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.title_goals),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    errorPlaceholderColor = Color.White,
                    unfocusedContainerColor = Color(0xffECECEC),
                    errorContainerColor = Color(0xFFE20000).copy(0.4f),
                    errorSupportingTextColor = Color(0xFFE20000),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                isError = checkInput && title.isEmpty(),
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = title, onValueChange = {
                    if (it.length <= 40) {
                        title = it
                    }
                },
                placeholder = {
                    Text(
                        text = "عنوان هدف را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                supportingText = {
                    if (checkInput && title.isEmpty()) {
                        Text(
                            text = "عنوان هدف را وارد کنید",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )
            SectionSelectedGoalsDate(
                currentDate = selectedDate,
                onSelectedDate = { selectedDate = it }
            )
            AnimatedVisibility(
                checkInput && selectedDate == null
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    text = "تاریخ را تنظیم کنید",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE20000),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.description_goals),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    errorPlaceholderColor = Color.White,
                    unfocusedContainerColor = Color(0xffECECEC),
                    errorContainerColor = Color(0xFFE20000).copy(0.4f),
                    errorSupportingTextColor = Color(0xFFE20000),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 6,
                minLines = 4,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = description, onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "توضیحات هدف را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
            Spacer(Modifier.height(12.dp))
        }
    }

}