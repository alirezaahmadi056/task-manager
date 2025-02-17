package ir.lrn.kara.ui.graph.medicine

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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.DialogDeleteItemTask
import ir.lrn.kara.util.Constants
import ir.lrn.kara.util.Constants.persianDayOfWeek
import ir.lrn.kara.viewModel.MedicineViewModel
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
    val medicines = remember(key1 = allMedicine) {
        allMedicine.sortedBy {
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
    val scope = rememberCoroutineScope()


    var singleId by remember { mutableStateOf<Int?>(null) }

    DialogDeleteItemTask(
        title = "حذف دارو",
        body = "از حذف این دارو اطمینان دارید؟",
        onBack = { singleId = null },
        show = singleId != null,
        onDeleteItem = {
            singleId?.let { medicineViewModel.deleteMedicineById(it) }
            singleId = null
        }
    )
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
                onBack = { navHostController.navigateUp() }
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
                onClick = { navHostController.navigate(Screen.AddMedicineScreen(day = dayWeek[pagerState.currentPage])) }
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
                .background(MaterialTheme.colorScheme.background),
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
            ) { notEmpty ->
                if (notEmpty) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        item { Spacer(Modifier.height(12.dp)) }
                        items(medicines, key = { item -> item.id }) { medicine ->
                            MedicineItemCard(
                                item = medicine,
                                onDeleted = { singleId = medicine.id },
                                onEdited = {
                                    navHostController.navigate(
                                        Screen.MedicineDetailScreen(
                                            medicine.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                } else {
                    MedicineEmpty(
                        modifier = Modifier
                            .fillMaxSize()
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
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "دارویی برای این بازه ثبت نشده است!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground

        )
    }
}