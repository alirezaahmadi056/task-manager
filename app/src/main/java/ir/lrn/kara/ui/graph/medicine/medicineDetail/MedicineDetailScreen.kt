package ir.lrn.kara.ui.graph.medicine.medicineDetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.util.TaskHelper.byLocate
import ir.lrn.kara.viewModel.MedicineViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MedicineDetailScreen(
    navHostController: NavHostController,
    id: Int,
    medicineViewModel: MedicineViewModel,
) {
    val currentDayStatus = remember { mutableStateListOf<String>() }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("7:00") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(id) {
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
    Scaffold(
        containerColor = Color.White,
        topBar = {
            MedicineDetailTopBar(
                image = (selectedImage ?: "").toString(),
                onBack = { navHostController.navigateUp() },
                onEdit = { navHostController.navigate(Screen.AddMedicineScreen(id)) }
            )
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
                    onClick = {
                        navHostController.navigateUp()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.return_text),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "",
                    tint = Color(0xff5A697D)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "ساعت مصرف : ${selectedTime.byLocate()} ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xff5A697D)
                )
            }
            Text(
                text = stringResource(R.string.description_medicine),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                color = Color.Black
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.Black
            )
        }
    }

}