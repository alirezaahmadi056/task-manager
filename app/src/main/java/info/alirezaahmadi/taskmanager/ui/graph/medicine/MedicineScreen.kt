package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.persianDayOfWeek
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun MedicineScreen(
    navHostController: NavHostController,
    medicineViewModel: MedicineViewModel
) {
    val day = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    val dayWeek = remember { Constants.deyWeek }
    val pagerState = rememberPagerState(initialPage = persianDayOfWeek[day] ?: 0) { dayWeek.size }
    val allMedicine by medicineViewModel.allMedicineItems.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MedicineTopBar(
                allDayWeek = dayWeek,
                currentPage = pagerState.currentPage,
                onSelected = { page ->
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page,
                            animationSpec = tween(500)
                        )
                    }
                },
                onBack = {navHostController.navigateUp()}
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = Color(0xff43A154),
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
        containerColor = Color(0xffC3D8C7)
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(Color.White),
            state = pagerState
        ) { page ->
            val currentMedicine = remember(key1 = page, key2 = allMedicine) {
                allMedicine.filter { it.dayWeek.contains(dayWeek[page]) }
                    .sortedBy {
                        val time = it.time
                        val parts = time.split(":")
                        if (parts.size == 2) {
                            val hours = parts[0].toIntOrNull() ?: 0
                            val minutes = parts[1].toIntOrNull() ?: 0
                            hours * 60 + minutes
                        } else {
                            Int.MAX_VALUE
                        }
                    }
            }
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