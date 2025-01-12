package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSkinRoutineScreen(
    navHostController: NavHostController
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
        topBar = {
            AddSkinRoutineTopBar { navHostController.navigateUp() }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp)
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
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White.copy(0.8f),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = title, onValueChange = { title = it },
                label = {
                    Text(
                        text = "عنوان روتین",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(
                        text = "عنوان روتین را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 2.dp),
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
                            .background(Color(0xffFFE3D5))
                            .border(1.dp, Color(0xff9D6B53), RoundedCornerShape(8.dp))
                            .clickable { showDialogSelectedTime = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "",
                            tint = Color(0xff5B3A2B),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.selected_hour),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Text(
                    text = selectedTime.byLocate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.clickable { showDialogSelectedTime = true }
                )
            }
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White.copy(0.8f),
                ),
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = body, onValueChange = { body = it },
                label = {
                    Text(
                        text = "توضیحات روتین",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
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
                onImageIndex = {currentImage=it}
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

