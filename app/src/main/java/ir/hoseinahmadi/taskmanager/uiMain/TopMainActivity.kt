package ir.hoseinahmadi.taskmanager.uiMain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.FormatListNumberedRtl
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.AlignVerticalBottom
import androidx.compose.material.icons.rounded.Approval
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.R
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.ui.component.showSelectedSortNotList
import ir.hoseinahmadi.taskmanager.ui.screen.notes.showDialogSelectedGridList
import ir.hoseinahmadi.taskmanager.util.Constants
import ir.hoseinahmadi.taskmanager.viewModel.DatStoreViewModel

@Composable
fun TopBar(
    backStackEntry: State<NavBackStackEntry?>,
    isShow: Boolean,
    openDrawer: () -> Unit,
) {

    if (isShow) {

        val isNote = backStackEntry.value?.destination?.route == Screen.NotesScreen.route
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    }
                        var expand by remember {
                            mutableStateOf(false)
                        }
                        Column {
                            IconButton(onClick = {
                                expand = true
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menu_dots),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.scrim.copy()
                                )
                            }
                            DropdownMenu(
                                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                expanded = expand,
                                onDismissRequest = { expand = false })
                            {
                                DropdownMenuItem(
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                    text = {
                                        Text(
                                            text = "ترتیب لیست",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.scrim

                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.FormatListNumberedRtl,
                                            contentDescription = "",
                                            tint = MaterialTheme.colorScheme.scrim
                                        )
                                    },
                                    onClick = {
                                        showSelectedSortNotList.value = true
                                        expand = false
                                    })

                                if (isNote) {
                                    HorizontalDivider(
                                        thickness = 0.5.dp,
                                        color = Color.LightGray
                                    )
                                    DropdownMenuItem(
                                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                        text = {
                                            Text(
                                                text = "نوع شاهده لیست",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.scrim
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Outlined.FilterList,
                                                contentDescription = "",
                                                tint = MaterialTheme.colorScheme.scrim
                                            )
                                        },
                                        onClick = {
                                            showDialogSelectedGridList.value = true
                                            expand = false
                                        })
                                }


                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = if (isNote) "یادداشت های من" else "وظایف من",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )

                IconButton(onClick = { openDrawer() }) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = ""
                    )
                }


            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray.copy(0.5f)
            )
        }
    }


}

@Composable
fun DrawerContent(
    navHostController: NavHostController,
    changeThem: (Boolean) -> Unit,
    onFinish: () -> Unit,
    datStoreViewModel: DatStoreViewModel = hiltViewModel(),
) {
    var darkThem by remember {
        mutableStateOf(Constants.isThemDark)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            painter = painterResource(id = R.drawable.taskhed),
            contentDescription = "",
        )
        DrawerItem(text = "درباره من", icon = Icons.Rounded.Approval,
            onClick = {
                navHostController.navigate(Screen.AboutMeScreen.route)
                onFinish()
            })
        DrawerItem(text = "درباره ما", icon = Icons.Rounded.AcUnit, onClick = {})
        DrawerItem(text = "درباره ما", icon = Icons.Rounded.AcUnit, onClick = {})
        DrawerItem(text = "پوسته تیره", icon = Icons.Rounded.DarkMode,
            addComposable = {
                Switch(
                    modifier = Modifier.height(10.dp),
                    thumbContent = {
                        if (darkThem) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "",
                                tint = Color.Blue
                            )
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedBorderColor = Color.Transparent
                    ),
                    checked = darkThem,
                    onCheckedChange = {
                        darkThem = it
                        changeThem(it)
                        datStoreViewModel.saveDarkThem(it)
                    }
                )
            },
            onClick = {
                darkThem = !darkThem
                changeThem(darkThem)
                datStoreViewModel.saveDarkThem(darkThem)
            })
        DrawerItem(
            text = "ارتباط با تیم توسعه دهنده",
            icon = Icons.Rounded.AlignVerticalBottom,
            onClick = {})

    }
}

@Composable
fun DrawerItem(
    text: String,
    icon: ImageVector,
    addComposable: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
                .padding(vertical = 11.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.scrim.copy(.9f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim,
                )
            }

            if (addComposable == null) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.scrim
                )
            } else {
                addComposable()
            }

        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 30.dp, end = 8.dp),
            thickness = 0.7.dp,
            color = Color.LightGray.copy(0.5f)
        )
    }
}