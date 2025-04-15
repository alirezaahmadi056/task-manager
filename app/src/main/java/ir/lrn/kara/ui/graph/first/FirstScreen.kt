package ir.lrn.kara.ui.graph.first

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.data.model.FirstRouteData
import ir.lrn.kara.data.model.FirstRouteData.Companion.fullFirstData
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.DrawerContent
import ir.lrn.kara.ui.component.MainDrawer
import ir.lrn.kara.util.Constants
import ir.lrn.kara.viewModel.DatStoreViewModel
import ir.lrn.kara.viewModel.ThemViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FirstScreen(
    navHostController: NavHostController,
    themViewModel: ThemViewModel,
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    SheetVideo(showSheet) { showSheet = false }

    val defaultList = remember { FirstRouteData.fullFirstData }

    val selectedUserIdList = remember { mutableStateListOf<Int>() }

    val activeUserList = remember(key1 = defaultList, key2 = selectedUserIdList.toList()) {
        defaultList.filter { it.id in selectedUserIdList }
    }
    LaunchedEffect(Unit) {
        selectedUserIdList.clear()
        selectedUserIdList.addAll(Constants.firstDataSet)
    }
    val visibilityState = remember { MutableTransitionState(false).apply { targetState = true } }

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
                        openHelp = {
                            showSheet = true
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(8.dp))
                    AnimatedVisibility(
                        visibleState = visibilityState,
                        enter = fadeIn(tween(500)) + slideInVertically(
                            animationSpec = tween(500),
                            initialOffsetY = { -40 }
                        ),
                        exit = fadeOut()
                    ) {
                        FirstTopPager(navHostController = navHostController)
                    }
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        maxItemsInEachRow = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        activeUserList.forEach { graph ->
                            AnimatedVisibility(
                                visibleState = visibilityState,
                                enter = fadeIn(tween(700)) + slideInVertically(
                                    animationSpec = tween(700),
                                    initialOffsetY = { -40 }
                                ),
                                exit = fadeOut()
                            ) {
                                SelectedGraphRoute(
                                    text = stringResource(graph.nameRes),
                                    image = graph.image
                                ) {
                                    navHostController.navigate(graph.route)
                                }
                            }
                        }
                        IntroductionSection()
                        Spacer(Modifier.height(12.dp))
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
            contentScale = ContentScale.Fit,
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
            .padding(4.dp),
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
        Spacer(Modifier.height(14.dp))
    }

}

fun getUserSelectedRoutes(
    enabledIds: Set<Int>,
    defaultsList: List<FirstRouteData>
): List<FirstRouteData> {
    return defaultsList.filter { it.id in enabledIds }
}