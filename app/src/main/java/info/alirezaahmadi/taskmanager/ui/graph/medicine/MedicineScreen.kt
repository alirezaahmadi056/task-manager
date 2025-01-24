package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineTimeFrame
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel

@Composable
fun MedicineScreen(
    navHostController: NavHostController,
    medicineViewModel: MedicineViewModel
) {
    var currentTimeFrame by remember { mutableStateOf(MedicineTimeFrame.HOURLY.name) }

    Scaffold(
        bottomBar = {
            MedicineBottomNavigation(
                currentTimeFrame = currentTimeFrame,
                onSelectedTimeFrame = { page -> currentTimeFrame = page }
            )
        },
        topBar = {
            MedicineTopBar { navHostController.navigateUp() }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Black,
                onClick = {
                    //navHostController.navigate()
                }
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start,
        containerColor = Color.White
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