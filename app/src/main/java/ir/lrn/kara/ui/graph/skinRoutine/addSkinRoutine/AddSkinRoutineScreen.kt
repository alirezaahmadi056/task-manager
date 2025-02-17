package ir.lrn.kara.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import ir.lrn.kara.R
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineItem
import ir.lrn.kara.data.db.skinRoutine.SkinStatus
import ir.lrn.kara.ui.component.CustomDataPickerDialog
import ir.lrn.kara.util.TaskHelper.byLocate
import ir.lrn.kara.viewModel.SkinRoutineViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSkinRoutineScreen(
    navHostController: NavHostController,
    id: Int,
    day: String?,
    time: String?,
    skinRoutineViewModel: SkinRoutineViewModel = hiltViewModel()
) {

    var currentTimeStatus by remember { mutableStateOf(SkinStatus.DAY.name) }
    val currentDayStatus = remember { mutableStateListOf<String>() }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var showDialogSelectedTime by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("7:00") }
    val initialMinute = remember(key1 = selectedTime) {
        try {
            val parts = selectedTime.split(":").map { it.toIntOrNull() ?: 0 }
            parts
        } catch (e: Exception) {
            listOf(7, 0)
        }
    }
    var currentImage by remember { mutableIntStateOf(0) }
    var currentColor by remember { mutableIntStateOf(0) }
    var checkInput by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        time?.let { currentTimeStatus = it }
        day?.let { currentDayStatus.add(it) }
        skinRoutineViewModel.getSkinRoutine(id).collectLatest { routine ->
            routine?.let { skin ->
                currentTimeStatus = skin.status
                title = skin.title
                body = skin.description
                selectedTime = skin.time
                currentImage = skin.image
                currentColor = skin.color
                currentDayStatus.addAll(skin.dayWeek)
            }
        }
    }
    CustomDataPickerDialog(
        isShow = showDialogSelectedTime,
        initialMinute = initialMinute.getOrElse(1) { 0 },
        initialHour = initialMinute.getOrElse(0) { 7 },
        onDismissRequest = { showDialogSelectedTime = false },
        onSelected = {
            val formattedMinute = String.format(Locale.US, "%02d", it.minute)
            selectedTime = "${it.hour}:${formattedMinute}"
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xffFFEDD8),
        topBar = { AddSkinRoutineTopBar { navHostController.navigateUp() } },
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
                        .fillMaxWidth(0.8f),
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 8.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff774936),
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (currentDayStatus.isNotEmpty() && title.isNotEmpty()) {
                            checkInput = false
                            skinRoutineViewModel.upsertSkinRoutine(
                                SkinRoutineItem(
                                    id = id,
                                    title = title,
                                    description = body,
                                    image = currentImage,
                                    color = currentColor,
                                    status = currentTimeStatus,
                                    time = selectedTime,
                                    dayWeek = currentDayStatus
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
            Spacer(modifier = Modifier.height(12.dp))
            SelectedSkinStatusSection(
                currentStatus = currentTimeStatus,
                onStatusSelectedStatus = { currentTimeStatus = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SelectedSkinDayStatusSection(
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
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 4.dp, start = 10.dp),
                text = "عنوان روتین",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = Color.LightGray,
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
                value = title, onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "عنوان روتین را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                supportingText = {
                    if (checkInput && title.isEmpty()) {
                        Text(
                            text = "عنوان روتین را وارد کنید",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            )

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
                            .border(1.dp, Color(0xff9D6B53), RoundedCornerShape(8.dp))
                            .clickable { showDialogSelectedTime = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.selected_hour),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Text(
                    text = selectedTime.byLocate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color =MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { showDialogSelectedTime = true }
                )
            }
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 4.dp, start = 10.dp),
                text = "توضیحات روتین",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = Color.LightGray,
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
                minLines = 3,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = body, onValueChange = { body = it },
                placeholder = {
                    Text(
                        text = "توضیحات روتین را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f),
                modifier = Modifier.padding(top = 15.dp, bottom = 8.dp)
            )
            SelectedSinImageSection(
                currentImageIndex = currentImage,
                onImageIndex = { currentImage = it }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            SelectedSkinColorSection(
                currentColor = currentColor,
                onColor = { currentColor = it }
            )

        }
    }
}

