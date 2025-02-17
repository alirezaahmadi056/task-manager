package ir.lrn.kara.ui.graph.goals.detail

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
import ir.lrn.kara.data.db.goals.GoalsItem
import ir.lrn.kara.data.db.goals.GoalsTimeFrame
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.util.TaskHelper.byLocate
import ir.lrn.kara.viewModel.GoalsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GoalsDetailScreen(
    navHostController: NavHostController,
    id: Int,
    goalsViewModel: GoalsViewModel
) {
    var selectedImage by remember { mutableStateOf<String?>(null) }
    var timeFrame by remember { mutableStateOf(GoalsTimeFrame.SHORT.name) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(id) {
        goalsViewModel.getGoalsById(id).collectLatest { goalsItem ->
            goalsItem?.let { goals ->
                selectedImage = goals.imageUri
                timeFrame = goalsItem.timeFrame
                title = goals.title
                description = goals.description
                selectedDate = goals.data
            }
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            GoalsDetailTopBar(
                image = selectedImage ?: "",
                onBack = { navHostController.navigateUp() },
                onEdit = { navHostController.navigate(Screen.AddGoalsScreen(id)) }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
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
                        containerColor = Color(0xff535CF0),
                        contentColor = Color.White
                    ),
                    onClick = {
                        goalsViewModel.upsertGoals(
                            GoalsItem(
                                imageUri = selectedImage ?: "",
                                data = selectedDate ?: "",
                                id = id,
                                title = title,
                                timeFrame = timeFrame,
                                description = description,
                                isCompleted = true
                            )
                        )
                        navHostController.navigateUp()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.completed_goals),
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
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = GoalsTimeFrame.entries.find { it.name == timeFrame }?.perName.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp, top = 8.dp),
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
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "فرصت انجام تا تاریخ : ${selectedDate.toString().byLocate()} ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(R.string.description_goals),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = description,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}