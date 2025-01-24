package info.alirezaahmadi.taskmanager.ui.graph.medicine.addMedicine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.ui.graph.goals.main.GoalsTopBar
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel

@Composable
fun AddMedicineScreen(
    navHostController: NavHostController,
    id: Int,
    time:String,
    medicineViewModel: MedicineViewModel
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var currentTimeFrame by remember { mutableStateOf(time) }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            GoalsTopBar(text = stringResource(if (id == 0) R.string.add_goals else R.string.update_goals)) {
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

        }
    }
}