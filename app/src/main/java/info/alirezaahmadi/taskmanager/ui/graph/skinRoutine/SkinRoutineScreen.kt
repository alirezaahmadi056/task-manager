package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.NightlightRound
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material.icons.rounded.Today
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.BaseTopBar
import info.alirezaahmadi.taskmanager.ui.graph.duties.task.getNextTaskId
import info.alirezaahmadi.taskmanager.ui.theme.LightGray
import info.alirezaahmadi.taskmanager.viewModel.SkinRoutineViewModel
import kotlinx.coroutines.launch

@Composable
fun SkinRoutineScreen(
    navHostController: NavHostController,
    skinRoutineViewModel: SkinRoutineViewModel = hiltViewModel()
) {
    val allSkinRoutine by skinRoutineViewModel.getAllSkinRoutine().collectAsState(emptyList())
    /*  val (dayRoutine, nightRoutine) = remember(key1 = allSkinRoutine) {
          allSkinRoutine.filter { it.status == SkinStatus.DAY.name }
      }*/
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SkinTopBar(
                currentPage = pagerState.currentPage,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(it) }
                },
                onBack = { navHostController.navigateUp() }
            )
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
                onClick = {

                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pagerState
        ) { }
    }

}

@Composable
fun SkinTopBar(
    currentPage: Int, onBack: () -> Unit,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .drawBehind {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, size.height), // شروع خط در پایین کامپوزبل
                    end = Offset(size.width, size.height), // پایان خط در پایین کامپوزبل
                    strokeWidth = 1.5.dp.toPx()
                )
            }
            .padding(horizontal = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 30.dp),
                text = "روتین پوستی من",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim
            )

            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack
            ) {
                Icon(
                    Icons.Rounded.ArrowForward,
                    contentDescription = ""
                )
            }
        }
        TabRow(
            containerColor = MaterialTheme.colorScheme.background,
            selectedTabIndex = currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(it[currentPage])
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)

                )
            },
            divider = {
                HorizontalDivider(thickness = 0.6.dp, color = Color.LightGray.copy(0.6f))
            }
        ) {
            Tab(
                unselectedContentColor = MaterialTheme.colorScheme.scrim.copy(0.8f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                text = {
                    Text(
                        text = "روز",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                onClick = { onClick(0) },
                icon = {
                    Icon(
                        imageVector = if (currentPage==0) Icons.Rounded.WbSunny else Icons.Outlined.WbSunny ,
                        contentDescription = "",
                    )
                },
                selected = currentPage == 0
            )

            Tab(
                unselectedContentColor = MaterialTheme.colorScheme.scrim.copy(0.8f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                text = {
                    Text(
                        text = "شب",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                onClick = { onClick(1) },
                icon = {
                    Icon(
                        imageVector =if (currentPage==1) Icons.Rounded.NightlightRound else Icons.Outlined.Nightlight ,
                        contentDescription = "",
                    )
                },
                selected = currentPage == 1
            )

        }

    }
}