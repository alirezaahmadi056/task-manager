package info.alirezaahmadi.taskmanager.ui.graph.medicine.addMedicine

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.ui.component.CustomDataPickerDialog
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine.SelectedSkinDayStatusSection
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    navHostController: NavHostController,
    id: Int,
    medicineViewModel: MedicineViewModel
) {

    val currentDayStatus = remember { mutableStateListOf<String>() }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var showDialogSelectedTime by remember { mutableStateOf(false) }
    var checkInput by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("7:00") }
    val initialMinute = remember(key1 = selectedTime) {
        try {
            val parts = selectedTime.split(":").map { it.toIntOrNull() ?: 0 }
            parts
        } catch (e: Exception) {
            listOf(7, 0)
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
        containerColor = Color.White,
        topBar = {
            CenterBackTopBar(text = stringResource(if (id == 0) R.string.add_goals else R.string.update_goals)) {
                navHostController.navigateUp()
            }
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
                        containerColor = Color(0xff43A154),
                        contentColor = Color.White
                    ),
                    onClick = {}
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
            Spacer(modifier = Modifier.height(15.dp))

        }
    }
}