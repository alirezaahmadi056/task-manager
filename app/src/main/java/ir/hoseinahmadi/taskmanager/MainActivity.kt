package ir.hoseinahmadi.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hoseinahmadi.taskmanager.navigation.BottomNavigation
import ir.hoseinahmadi.taskmanager.navigation.NavGraph
import ir.hoseinahmadi.taskmanager.navigation.Screen
import ir.hoseinahmadi.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkThem by rememberSaveable {
                mutableStateOf(false)
            }
            navHostController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val backStackEntry = navHostController.currentBackStackEntryAsState()
            val item = listOf(
                Screen.NotesScreen.route,
                Screen.TaskScreen.route,
            )
            val showBottomBar = backStackEntry.value?.destination?.route in item.map { it }
            TaskManagerTheme(darkTheme = darkThem) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ModalNavigationDrawer(
                        gesturesEnabled = drawerState.isOpen,
                        drawerState = drawerState,
                        drawerContent = {
                            DrawerContent(
                                changeThem = {
                                    darkThem = it
                                }
                            )
                        }) {
                        Scaffold(
                            topBar = {
                                TopBar(showBottomBar,
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    changeThem = {
                                        darkThem = !darkThem
                                    }
                                )
                            },
                            bottomBar = {
                                BottomNavigation(
                                    navHostController = navHostController,
                                    isShow = showBottomBar
                                )
                            },
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) {
                                NavGraph(navHostController)
                            }
                        }
                    }

                }
            }
        }
    }
}


@Composable
private fun TopBar(
    isShow: Boolean,
    onClick: () -> Unit,
    changeThem: () -> Unit,
) {
    if (isShow) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 2.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onClick() }) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = ""
                    )
                }

                Text(text = "یادداشت های من")
                IconButton(onClick = { changeThem() }) {
                    Icon(
                        Icons.AutoMirrored.Rounded.Sort, contentDescription = ""
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.6f)
            )
        }
    }


}

@Composable
private fun DrawerContent(
    changeThem: (Boolean) -> Unit
) {
    var darkThem by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            painter = painterResource(id = R.drawable.taskhed),
            contentDescription = "",
        )
        DrawerItem(text = "درباره ما", icon = Icons.Rounded.AcUnit, onClick = {})
        DrawerItem(text = "درباره ما", icon = Icons.Rounded.AcUnit, onClick = {})
        DrawerItem(text = "درباره ما", icon = Icons.Rounded.AcUnit,
            addComposable = {
                    Switch(checked = darkThem,
                        onCheckedChange = {
                            darkThem =it
                            changeThem(it)
                        }
                    )
            },
            onClick = {})

    }
}

@Composable
private fun DrawerItem(
    text: String,
    icon: ImageVector,
    addComposable: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 5.dp),
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
                    tint = MaterialTheme.colorScheme.scrim,
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
            thickness = 0.9.dp,
            color = Color.LightGray.copy(0.5f)
        )
    }
}