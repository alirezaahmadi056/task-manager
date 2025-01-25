package info.alirezaahmadi.taskmanager.ui.graph.dreame

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel

@Composable
fun DreamScreen(
    navHostController: NavHostController,
    dreamViewModel: DreamViewModel
) {
    Scaffold(
        topBar = {
            CenterBackTopBar(text = stringResource(R.string.my_dream)) {
                navHostController.navigateUp()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = Color.Black,
                expanded = true,
                text = {
                    Text(
                        text = "رویای جدید",
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
                onClick = { navHostController.navigate(Screen.AddDreamsScreen()) }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }

}