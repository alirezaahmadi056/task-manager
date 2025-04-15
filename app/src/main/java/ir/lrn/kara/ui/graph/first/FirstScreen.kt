package ir.lrn.kara.ui.graph.first

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ir.lrn.kara.ui.component.DrawerContent
import ir.lrn.kara.ui.component.MainDrawer
import ir.lrn.kara.util.Constants
import ir.lrn.kara.viewModel.DatStoreViewModel
import ir.lrn.kara.viewModel.ThemViewModel
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
    var showSheetVideo by remember { mutableStateOf(false) }
    var showSheetCustomList by remember { mutableStateOf(false) }
    SheetVideo(showSheetVideo) { showSheetVideo = false }

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
    SheetCustomList(
        isShow = showSheetCustomList,
        allList = defaultList,
        activeId = defaultList.filter { it.id in selectedUserIdList },
        updateId = { newIds ->
            selectedUserIdList.clear()
            selectedUserIdList.addAll(newIds)
            datStoreViewModel.saveEnabledRoutes(newIds)
            Constants.firstDataSet = newIds
        },
        onDismiss = { showSheetCustomList = false }
    )
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
                        openHelp = { showSheetVideo = true },
                        openSheetEdit = { showSheetCustomList = true }
                    )
                },
                bottomBar = { IntroductionSection() }
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
                                enter = fadeIn(tween(800)) + slideInVertically(
                                    animationSpec = tween(800),
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

                    }
                    if (activeUserList.isEmpty()) {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = { showSheetCustomList =true }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "No Items",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "هیچ آیتم فعالی وجود ندارد! برای ویرایش کلیک کنید.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

        }
    )
}

@Composable
private fun FistTopBar(
    openMenu: () -> Unit,
    openHelp: () -> Unit,
    openSheetEdit: () -> Unit
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
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = openSheetEdit
            ) {
                Icon(
                    painter = painterResource(R.drawable.layout_edit),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(Modifier.width(2.dp))
            IconButton(
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
