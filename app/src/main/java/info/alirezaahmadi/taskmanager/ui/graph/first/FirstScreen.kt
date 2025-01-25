package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DrawerContent
import info.alirezaahmadi.taskmanager.ui.component.MainDrawer
import info.alirezaahmadi.taskmanager.ui.theme.LightGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FirstScreen(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MainDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navHostController = navHostController,
                isOpen = drawerState.isOpen,
                changeThem = { },
                onFinish = {
                    scope.launch {
                        drawerState.close()
                    }
                },
            )
        },
        content = {
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                maxLines = 2,
                horizontalArrangement = Arrangement.Center
            ) {
                SelectedGraphRoute(
                    text = "وظایف",
                ) { navHostController.navigate(Screen.DutiesGraph) }

                SelectedGraphRoute(
                    text = "روتیم",
                ) { navHostController.navigate(Screen.WeeklyRoutineGraph) }
                SelectedGraphRoute(
                    text = "پوستی",
                ) { navHostController.navigate(Screen.SkinRoutineGraph) }

                SelectedGraphRoute(
                    text = "باشگاه",
                ) { navHostController.navigate(Screen.ExerciseProgramGraph) }

                SelectedGraphRoute(
                    text = "اهداف",
                ) { navHostController.navigate(Screen.GoalsGraph) }

                SelectedGraphRoute(
                    text = "دارو",
                ) { navHostController.navigate(Screen.MedicineGraph) }
                SelectedGraphRoute(
                    text = "رویا",
                ) { navHostController.navigate(Screen.DreamGraph) }

            }
        })
}

@Composable
fun SelectedGraphRoute(
    text: String,
    @DrawableRes image: Int = R.drawable.lesson,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Center
        )
    }

}