package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.bottomNavigation.SkinBottomNavigation
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.persianDayOfWeek
import info.alirezaahmadi.taskmanager.viewModel.SkinRoutineViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun SkinRoutineScreen(
    navHostController: NavHostController,
    skinRoutineViewModel: SkinRoutineViewModel = hiltViewModel()
) {
    val day = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    val dayWeek = remember { Constants.deyWeek }
    val pagerState = rememberPagerState(initialPage = persianDayOfWeek[day] ?: 0) { dayWeek.size }
    val allSkinRoutine by skinRoutineViewModel.getAllSkinRoutine().collectAsState(emptyList())
    var currentSkinStatus by rememberSaveable { mutableIntStateOf(0) }
    val filterStatus = remember(currentSkinStatus) {
        when (currentSkinStatus) {
            0 -> SkinStatus.DAY.name
            1 -> SkinStatus.AFTERNOON.name
            2 -> SkinStatus.NIGHT.name
            else -> SkinStatus.DAY.name
        }
    }

    val routines = remember(allSkinRoutine, filterStatus) {
        allSkinRoutine.filter { it.status == filterStatus }
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
    val coroutineScope = rememberCoroutineScope()
    var showDialogDeleted by remember { mutableStateOf(false) }
    var singleRoutineItem by remember { mutableStateOf<SkinRoutineItem?>(null) }

    DialogDeleteItemTask(
        show = showDialogDeleted,
        title = "حذف روتین",
        onBack = { showDialogDeleted = false },
        body = "از حذف کردن این روتین پوستی اطمینان دارید",
        onDeleteItem = {
            singleRoutineItem?.id?.let { skinRoutineViewModel.deletedSkinRoutine(it) }
            showDialogDeleted = false
            singleRoutineItem = null
        }
    )

    Scaffold(
        containerColor = Color(0xffFFEDD8),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SkinTopBar(
                allDayWeek = dayWeek,
                currentPage = pagerState.currentPage,
                onSelected = { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page, animationSpec = tween(600))
                    }
                },
                onBack = {navHostController.navigateUp()}
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = Color.Black,
                expanded = true,
                text = {
                    Text(
                        text = "روتین جدید",
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
        bottomBar = {
            SkinBottomNavigation(
                currentPage = currentSkinStatus,
                onSelectedPage = { currentSkinStatus = it }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(Color.White)
        ) { page ->
            val currentRoutine = remember(key1 = page, key2 = routines) {
                filterRoutinesByDay(day = dayWeek[page], routines = routines)
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(Modifier.height(15.dp)) }
                if (currentRoutine.isNotEmpty()) {
                    skinRoutine(
                        items = currentRoutine,
                        onDeleted = {
                            singleRoutineItem = it
                            showDialogDeleted = true
                        },
                        onEdited = { navHostController.navigate(Screen.AddSkinRoutineScreen(it.id)) }
                    )
                } else {
                    item {
                        Text(
                            "روتین برای یان بازه تنظیم نشده"
                        )
                    }

                }

            }
        }
    }
}


private fun LazyListScope.skinRoutine(
    items: List<SkinRoutineItem>,
    onEdited: (SkinRoutineItem) -> Unit,
    onDeleted: (SkinRoutineItem) -> Unit
) {
    items(items = items, key = { it.id }) { routine ->
        SkinRoutineItemCard(
            item = routine,
            onEdited = { onEdited(routine) },
            onDeleted = { onDeleted(routine) }
        )
    }
}

fun filterRoutinesByDay(day: String, routines: List<SkinRoutineItem>): List<SkinRoutineItem> {
    return routines.filter { it.dayWeek.contains(day) }
}