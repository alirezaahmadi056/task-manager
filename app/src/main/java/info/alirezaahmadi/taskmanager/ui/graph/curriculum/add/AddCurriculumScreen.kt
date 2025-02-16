package info.alirezaahmadi.taskmanager.ui.graph.curriculum.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import info.alirezaahmadi.taskmanager.viewModel.CurriculumViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCurriculumScreen(
    navHostController: NavHostController,
    id: Int?,
    day:String?=null,
    curriculumViewModel: CurriculumViewModel
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("7:00") }
    var endTime by remember { mutableStateOf("8:00") }
    var checkInput by remember { mutableStateOf(false) }
    val currentDayStatus = remember { mutableStateListOf<String>() }
    var showDialogSelectedTime: Pair<Boolean, String> by remember {
        mutableStateOf(Pair(false, ""))
    }
    LaunchedEffect(id) {
        day?.let {currentDayStatus.add(it)  }
        if (id != null){
            curriculumViewModel.getCurriculum(id).collectLatest {curriculum->
                curriculum?.let {
                    title =it.title
                    description =it.description
                    startTime =it.startTime
                    endTime =it.endTime
                    currentDayStatus.clear()
                    currentDayStatus.addAll(it.dayWeek)
                }
            }
        }
    }
    CustomDataPickerDialog(
        isShow = showDialogSelectedTime.first,
        initialMinute = 0,
        initialHour = 7,
        onDismissRequest = { showDialogSelectedTime = Pair(false, "") },
        onSelected = {
            val formattedMinute = String.format(Locale.US, "%02d", it.minute)
            if (showDialogSelectedTime.second == "end") {
                endTime = "${it.hour}:${formattedMinute}"
            } else {
                startTime = "${it.hour}:${formattedMinute}"
            }
        }
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = { AddCurriculumTopBar(id) { navHostController.navigateUp() } },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
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
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = {
                        if (currentDayStatus.isNotEmpty() && title.isNotEmpty()) {
                            checkInput = false
                            curriculumViewModel.upsertCurriculum(
                                CurriculumItem(
                                    id = id ?: 0,
                                    title = title,
                                    description = description,
                                    dayWeek = currentDayStatus,
                                    startTime = startTime,
                                    endTime = endTime
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
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            SelectedCurriculumDayStatusSection(
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
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.name_lesson),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
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
                        text = "نام درس را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                supportingText = {
                    if (checkInput && title.isEmpty()) {
                        Text(
                            text = "نام درس را وارد کنید",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            )
            //start time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                            .clickable { showDialogSelectedTime = Pair(true, "start") }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.start_time),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Text(
                    text = startTime.byLocate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { showDialogSelectedTime = Pair(true, "start") }
                )
            }

            //end time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                            .clickable { showDialogSelectedTime = Pair(true, "end") }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.end_time),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Text(
                    text = endTime.byLocate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { showDialogSelectedTime = Pair(true, "end") }
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.description_lesson),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = Color(0xffECECEC),
                    errorContainerColor = Color(0xFFE20000).copy(0.4f),
                    errorSupportingTextColor = Color(0xFFE20000),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 6,
                minLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = description, onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "توضیحات درس را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
        }
    }
}