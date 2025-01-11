package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.addSkinRoutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus

@Composable
fun AddSkinRoutineScreen(
    navHostController: NavHostController
) {

    var currentTimeStatus by remember { mutableStateOf(SkinStatus.DAY.name) }
    val currentDayStatus = remember { mutableStateListOf<String>() }
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
                currentSList =currentDayStatus,
                onAddDay = { currentDayStatus.add(it) },
                onRemoveDay = { currentDayStatus.remove(it) }
            )
        }
    }
}

