package info.alirezaahmadi.taskmanager.ui.graph.goals.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsScreen(
    navHostController: NavHostController,
    goalsViewModel: GoalsViewModel
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            GoalsTopBar(text = stringResource(R.string.my_goals)) { navHostController.navigateUp() }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { GoalsTopSection(navHostController, goalsViewModel) }
            item{MoviesAndBooksSection(goalsViewModel)}
        }
    }
}