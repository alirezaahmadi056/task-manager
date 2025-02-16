package info.alirezaahmadi.taskmanager.ui.graph.medicine.addMedicine

import android.net.Uri
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
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineItem
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    navHostController: NavHostController,
    id: Int,
    day: String? = null,
    medicineViewModel: MedicineViewModel
) {
    val currentDayStatus = remember { mutableStateListOf<String>() }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDialogSelectedTime by remember { mutableStateOf(false) }
    var checkInput by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("7:00") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val initialMinute = remember(key1 = selectedTime) {
        try {
            val parts = selectedTime.split(":").map { it.toIntOrNull() ?: 0 }
            parts
        } catch (e: Exception) {
            listOf(7, 0)
        }
    }
    LaunchedEffect(id) {
        day?.let { currentDayStatus.add(it) }
        medicineViewModel.getMedicineById(id).collectLatest { medicines ->
            medicines?.let { medicine ->
                title = medicine.title
                description = medicine.description
                selectedTime = medicine.time
                selectedImage = Uri.parse(medicine.image)
                currentDayStatus.addAll(medicine.dayWeek)
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterBackTopBar(
                text = stringResource(if (id == 0) R.string.add_medicine else R.string.update_medicine),
                backColor = Color(0xffC3D8C7),
                textColor = Color.Black
            ) {
                navHostController.navigateUp()
            }
        },
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
                        containerColor = Color(0xff43A154),
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (
                            currentDayStatus.toList()
                                .isNotEmpty() && title.isNotEmpty() && selectedTime.isNotEmpty()
                        ) {
                            checkInput = false
                            medicineViewModel.upsertMedicineItem(
                                MedicineItem(
                                    id = id,
                                    title = title,
                                    description = description,
                                    time = selectedTime,
                                    image = (selectedImage ?: "").toString(),
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
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SelectedMedicineStatusSection(
                currentSList = currentDayStatus,
                onAddDay = { currentDayStatus.add(it) },
                onRemoveDay = { currentDayStatus.remove(it) },
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
                modifier = Modifier.padding(top = 15.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.title_medicine),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = Color.DarkGray,
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
                    if (it.length <= 30) {
                        title = it
                    }
                },
                placeholder = {
                    Text(
                        text = "عنوان دارو را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                supportingText = {
                    if (checkInput && title.isEmpty()) {
                        Text(
                            text = "عنوان دارو را وارد کنید",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 8.dp, start = 10.dp),
                text = stringResource(R.string.description_medicine),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = Color.DarkGray,
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
                maxLines = 4,
                minLines = 4,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = description, onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "توضیحات دارو را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 5.dp),
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
                            .background(Color(0xff43A154))
                            .clickable { showDialogSelectedTime = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "",
                            tint = Color.White,
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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { showDialogSelectedTime = true }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            AddImageMedicineSection(
                uri = selectedImage,
                onImageSelected = { selectedImage = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}