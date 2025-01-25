package info.alirezaahmadi.taskmanager.ui.graph.dreame.dreamDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DreamDetailScreen(
    navHostController: NavHostController,
    id: Int,
    dreamViewModel: DreamViewModel,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val imageList = remember { mutableStateListOf<String>() }
    LaunchedEffect(id) {
        dreamViewModel.getDreamById(id).collectLatest { dream ->
            dream?.let { item ->
                title = item.title
                description = item.description
                imageList.clear()
                imageList.add(item.coverUri)
                imageList.addAll(item.imageUriList)
            }
        }
    }
    var singleId by remember { mutableStateOf<Int?>(null) }

    DialogDeleteItemTask(
        title = "حذف رویا",
        body = "از حذف این رویا اطمینان دارید؟",
        onBack = { singleId = null },
        show = singleId != null,
        onDeleteItem = {
            singleId?.let { dreamViewModel.deleteDreamByID(it) }
            navHostController.navigateUp()
            singleId = null
        }
    )
    Scaffold(
        topBar = {
            DreamDetailTopBar(
                onBack = {navHostController.navigateUp()},
                onEdit = { navHostController.navigate(Screen.AddDreamsScreen(id = id)) },
                onDeleted = { singleId =id }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                TextButton (
                    modifier = Modifier
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.
                    textButtonColors(contentColor = Color(0xff535CF0)),
                    onClick = {

                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "رویا محقق شد",
                        style = MaterialTheme.typography.labelSmall,
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
        ) {

        }
    }
}