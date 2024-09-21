package info.alirezaahmadi.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import info.alirezaahmadi.taskmanager.navigation.BottomNavigation
import info.alirezaahmadi.taskmanager.navigation.NavGraph
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.AppConfig
import info.alirezaahmadi.taskmanager.ui.theme.TaskManagerTheme
import info.alirezaahmadi.taskmanager.topBarMain.DrawerContent
import info.alirezaahmadi.taskmanager.topBarMain.TopBar
import info.alirezaahmadi.taskmanager.util.Constants
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppConfig()
            var darkThem by rememberSaveable {
                mutableStateOf(Constants.isThemDark)
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
                            DrawerContent(navHostController, isOpen = drawerState.isOpen, changeThem = { darkThem = it },
                                onFinish ={
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                            )
                        }) {
                        Scaffold(
                            topBar = {
                                TopBar(
                                    navHostController,
                                    backStackEntry,
                                    showBottomBar,
                                    openDrawer = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                )
                            },
                            bottomBar = {
                                BottomNavigation(
                                    navHostController = navHostController,
                                    isShow = showBottomBar,
                                 backStackEntry=   backStackEntry
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


