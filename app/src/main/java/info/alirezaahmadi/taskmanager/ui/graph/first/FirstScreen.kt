package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DrawerContent
import info.alirezaahmadi.taskmanager.ui.component.MainDrawer
import info.alirezaahmadi.taskmanager.viewModel.ThemViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FirstScreen(
    navHostController: NavHostController,
    themViewModel: ThemViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MainDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navHostController = navHostController,
                isOpen = drawerState.isOpen,
                themViewModel = themViewModel,
                onFinish = {
                    scope.launch {
                        drawerState.close()
                    }
                },
            )
        },
        content = {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    FistTopBar(
                        openMenu = { scope.launch { drawerState.open() } },
                        openHelp = {}
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Spacer(Modifier.height(8.dp))
                    FirstTopPager(navHostController = navHostController)
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SelectedGraphRoute(
                            text = "مدیریت وظایف ",
                            image = R.drawable.duties_image
                        ) { navHostController.navigate(Screen.DutiesGraph) }

                        SelectedGraphRoute(
                            text = "روتین هفتگی",
                            image = R.drawable.weekly_routine_image
                        ) { navHostController.navigate(Screen.WeeklyRoutineGraph) }
                        SelectedGraphRoute(
                            text = "روتین پوستی",
                            image = R.drawable.skin_routine_image
                        ) { navHostController.navigate(Screen.SkinRoutineGraph) }

                        SelectedGraphRoute(
                            text = "برنامه باشگاه",
                            image = R.drawable.ecericies_image
                        ) { navHostController.navigate(Screen.ExerciseProgramGraph) }

                        SelectedGraphRoute(
                            text = "اهداف من",
                            image = R.drawable.goals_image
                        ) { navHostController.navigate(Screen.GoalsGraph) }

                        SelectedGraphRoute(
                            text = "برنامه دارویی",
                            image = R.drawable.medicine_image
                        ) { navHostController.navigate(Screen.MedicineGraph) }
                        SelectedGraphRoute(
                            text = "رویاهای من",
                            image = R.drawable.dream_image
                        ) { navHostController.navigate(Screen.DreamGraph) }

                    }
                }

            }

        })
}

@Composable
private fun FistTopBar(
    openMenu: () -> Unit,
    openHelp: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, size.height), // شروع خط در پایین کامپوزبل
                    end = Offset(size.width, size.height), // پایان خط در پایین کامپوزبل
                    strokeWidth = 1.5.dp.toPx()
                )
            }
            .padding(9.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = openMenu
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Image(
            painter = painterResource(R.drawable.first_top_logo),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center),
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = openHelp
        ) {
            Icon(
                painter = painterResource(R.drawable.message_question),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun SelectedGraphRoute(
    text: String,
    @DrawableRes image: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier
                .size(75.dp),
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
        Spacer(Modifier.height(6.dp))
    }

}