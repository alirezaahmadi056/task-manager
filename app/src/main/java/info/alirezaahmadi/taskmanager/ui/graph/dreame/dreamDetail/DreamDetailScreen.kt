package info.alirezaahmadi.taskmanager.ui.graph.dreame.dreamDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel

@Composable
fun DreamDetailScreen(
    navHostController: NavHostController,
    id: Int,
    dreamViewModel: DreamViewModel,
) {
    var singleId by remember { mutableStateOf<Int?>(null) }

    DialogDeleteItemTask(
        title = "حذف رویا",
        body = "از حذف این رویا اطمینان دارید؟",
        onBack = { singleId = null },
        show = singleId != null,
        onDeleteItem = {
            singleId?.let { dreamViewModel.deleteDreamByID(it) }
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