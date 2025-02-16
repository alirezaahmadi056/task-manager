package info.alirezaahmadi.taskmanager.ui.graph.curriculum.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.component.EmptyList
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.persianDayOfWeek
import info.alirezaahmadi.taskmanager.viewModel.CurriculumViewModel
import java.util.Calendar

@Composable
fun CurriculumScreen(
    navHostController: NavHostController,
    curriculumViewModel: CurriculumViewModel
) {
    val day = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    val dayWeek = remember { Constants.deyWeek }
    val pagerState = rememberPagerState(initialPage = persianDayOfWeek[day] ?: 0) { dayWeek.size }

    val allCurriculumItems by curriculumViewModel.getAllCurriculum().collectAsState(emptyList())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = {
            CurriculumTopBar(
                pagerState = pagerState,
                allTabs = dayWeek,
                onBack = { navHostController.navigateUp() }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                expanded = true,
                text = {
                    Text(
                        text = "درس",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.NoteAdd,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                onClick = { navHostController.navigate(Screen.AddCurriculumScreen(day = dayWeek[pagerState.currentPage])) }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
    ) { innerPadding ->
        HorizontalPager(
            key = { "pageKey:${dayWeek[it]}" },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.Top,
            state = pagerState,
        ) { page ->
            val currentCurriculum = remember(
                page,
                allCurriculumItems
            ) { allCurriculumItems.filter { it.dayWeek.contains(dayWeek[page]) }.sortedBy {
                val time = it.startTime
                val parts = time.split(":")
                if (parts.size == 2) {
                    val hours = parts[0].toIntOrNull() ?: 0
                    val minutes = parts[1].toIntOrNull() ?: 0
                    hours * 60 + minutes
                } else { Int.MAX_VALUE }
            } }
            Curriculum(
                items = currentCurriculum,
                currentDay = dayWeek[page],
                curriculumViewModel = curriculumViewModel,
                navHostController = navHostController
            )
        }
    }
}

@Composable
private fun Curriculum(
    items: List<CurriculumItem>,
    currentDay: String,
    navHostController: NavHostController,
    curriculumViewModel: CurriculumViewModel
) {
    var singleId by remember { mutableStateOf<Int?>(null) }
    DialogDeleteItemTask(
        title = "حذف درس",
        body = "آیا از حذف این درس اطمینان دارید؟",
        show = singleId != null,
        onBack = { singleId = null },
        onDeleteItem = {
            singleId?.let { curriculumViewModel.deleteCurriculum(id = it) }
            singleId = null
        }
    )
    AnimatedContent(
        targetState = items.isNotEmpty(), label = ""
    ) { notEmpty ->
        if (notEmpty) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(Modifier.height(15.dp)) }
                items(items = items, key = { it.id }) { curriculum ->
                    CurriculumItemCard(
                        item = curriculum,
                        onEdited = { navHostController.navigate(Screen.AddCurriculumScreen(curriculum.id)) },
                        onDelete = { singleId = curriculum.id }
                    )
                }
            }
        } else {
            EmptyList("برنامه ای برای روز $currentDay ثبت نکرده اید! ")
        }
    }
}