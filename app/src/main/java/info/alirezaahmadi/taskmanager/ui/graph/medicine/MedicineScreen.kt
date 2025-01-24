package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineTimeFrame
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel

@Composable
fun MedicineScreen(
    navHostController: NavHostController,
    medicineViewModel: MedicineViewModel
) {
    val allMedicine by medicineViewModel.getAllMedicine().collectAsState(emptyList())
    var currentTimeFrame by remember { mutableStateOf(MedicineTimeFrame.HOURLY.name) }
    val currentMedicine = remember(key1 = allMedicine, key2 = currentTimeFrame) {
        allMedicine.filter { it.timeFrame == currentTimeFrame }
    }
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
            ExtendedFloatingActionButton(
                containerColor = Color.Black,
                expanded = true,
                text = {
                    Text(
                        text = "دارو جدید",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                onClick = { navHostController.navigate(Screen.AddSkinRoutineScreen()) }
            )
        },

        floatingActionButtonPosition = FabPosition.Start,
        containerColor = Color.White
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentMedicine.isNotEmpty(),
            label = ""
        ) {
            if (it) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 12.dp)
                ) {

                }
            } else {
                MedicineEmpty(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }

    }
}

@Composable
private fun MedicineEmpty(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.EmojiPeople,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "دارویی برای این بازه ثبت نشده است!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}