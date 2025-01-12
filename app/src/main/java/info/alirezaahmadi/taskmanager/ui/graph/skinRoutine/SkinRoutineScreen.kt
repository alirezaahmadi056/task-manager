package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.bottomNavigation.SkinBottomNavigation
import info.alirezaahmadi.taskmanager.viewModel.SkinRoutineViewModel
import kotlinx.coroutines.launch

@Composable
fun SkinRoutineScreen(
    navHostController: NavHostController,
    skinRoutineViewModel: SkinRoutineViewModel = hiltViewModel()
) {
    val allSkinRoutine by skinRoutineViewModel.getAllSkinRoutine().collectAsState(emptyList())
    var currentSkinStatus by rememberSaveable  { mutableIntStateOf(0) }
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
    }



    val pagerState = rememberPagerState { 2 }
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
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = true,
                text = {
                    Text(
                        text = "روتین پوستی",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.NoteAdd,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { Spacer(Modifier.height(8.dp)) }
            skinRoutine(
                items = routines,
                onDeleted = {
                    singleRoutineItem = it
                    showDialogDeleted = true
                },
                onEdited = { navHostController.navigate(Screen.AddSkinRoutineScreen(it.id)) }
            )

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