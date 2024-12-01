package info.alirezaahmadi.taskmanager.topBarMain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.FormatListNumberedRtl
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.showSelectedSortNotList
import info.alirezaahmadi.taskmanager.ui.screen.notes.showDialogSelectedGridList
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.DatStoreViewModel

@Composable
fun TopBar(
    navHostController: NavHostController,
    openDrawer: () -> Unit,
    pagerState: PagerState,
) {
    Box(
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
            .padding(vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = pagerState.currentPage != 2,
            ) {
                IconButton(onClick = {
                    navHostController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.scrim
                    )
                }
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

                    if (pagerState.currentPage == 0) {
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
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 30.dp),
            text = when (pagerState.currentPage) {
                0 -> "یادداشت های من"
                1 -> "وظایف من"
                else -> "روتین های من"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.scrim
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = openDrawer
        ) {
            Icon(
                Icons.Rounded.Menu,
                contentDescription = ""
            )
        }


    }


}


@Composable
fun DrawerContent(
    navHostController: NavHostController,
    isOpen: Boolean,
    changeThem: (Boolean) -> Unit,
    onFinish: () -> Unit,
    datStoreViewModel: DatStoreViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity
    val uriHandler = LocalUriHandler.current

    var darkThem by remember {
        mutableStateOf(Constants.isThemDark)
    }

    var showAlertSendMessage by remember {
        mutableStateOf(false)
    }

    var showAlertNoInternet by remember {
        mutableStateOf(false)
    }
    BackHandler(enabled = isOpen) {
        onFinish()
    }
    AlertDialogSendMessage(
        show = showAlertSendMessage,
        onDismissRequest = {
            showAlertSendMessage = false
        }
    )
    NoInterNet(show = showAlertNoInternet,
        navHostController = navHostController, context = context,
        onFinish = { showAlertNoInternet = false })


    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            painter = painterResource(id = R.drawable.taskhed),
            contentDescription = "",
        )
        DrawerItem(text = "درباره من", icon = Icons.Outlined.Person,
            onClick = {
                if (isOnline(context)) {
                    navHostController.navigate(Screen.AboutMeScreen.route)
                } else {
                    showAlertNoInternet = true
                }
                onFinish()
            })
        DrawerItem(text = "سایر برنامه ها", icon = Icons.Outlined.Apps,
            onClick = {
                try {
                    uriHandler.openUri("https://cafebazaar.ir/developer/877223876871")
                } catch (e: Exception) {
                    Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
                }
                onFinish()
            })
        DrawerItem(text = "پیشنهادات", icon = Icons.Outlined.QuestionAnswer,
            onClick = {
                showAlertSendMessage = true
                onFinish()
            })
        DrawerItem(
            text = "اشتراک گذاری برنامه",
            icon = Icons.Outlined.Share,
            onClick = {
                onFinish()
                shareLink(
                    context,
                    url = "https://cafebazaar.ir/app/?id=info.alirezaahmadi.taskmanager&ref=share"
                )
            },
        )
        DrawerItem(
            text = "امتیاز به برنامه",
            icon = Icons.Outlined.Stars,
            onClick = {
                onFinish()
                comments(context)
            },
        )
        DrawerItem(text = "پوسته تیره", icon = Icons.Outlined.DarkMode,
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
                        Constants.isThemDark = it
                        datStoreViewModel.saveDarkThem(it)
                    }
                )
            },
            onClick = {
                darkThem = !darkThem
                changeThem(darkThem)
                Constants.isThemDark = darkThem
                datStoreViewModel.saveDarkThem(darkThem)
            })


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
            thickness = 0.5.dp,
            color = Color.LightGray.copy(0.5f)
        )
    }
}

@Composable
private fun NoInterNet(
    show: Boolean,
    navHostController: NavHostController,
    context: Activity,
    onFinish: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onFinish() },
            title = {
                Text(
                    text = "عدم اتصال اینترنت",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.scrim
                )
            },
            text = {
                Text(
                    text = "لطفا دسترسی به اینترنت را بررسی کنید!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        if (isOnline(context)) {
                            onFinish()
                            navHostController.navigate(Screen.AboutMeScreen.route)
                        } else {
                            Toast.makeText(context, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }) {
                    Text(
                        text = "تلاش مجدد",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

            },
            dismissButton = {
                TextButton(onClick = { onFinish() }) {
                    Text(
                        text = "بستن",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
            }
        )
    }

}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}


fun shareLink(context: Context, url: String) {
    try {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share link via"))
    } catch (e: Exception) {
        Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
    }
}

fun comments(context: Context) {
    val intent = Intent(Intent.ACTION_EDIT).apply {
        setData(Uri.parse("bazaar://details?id=" + context.packageName))
        setPackage("com.farsitel.bazaar")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
    }

}