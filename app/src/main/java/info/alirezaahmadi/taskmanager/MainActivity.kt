package info.alirezaahmadi.taskmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.app.ActivityCompat
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
            navHostController = rememberNavController()
            checkPermissions()
            TaskApp(navHostController)
        }
    }
    private fun checkPermissions() {
        // Check for both notifications and alarms on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.SCHEDULE_EXACT_ALARM
                    ),
                    1
                )
            }
        } else {
            // For Android 12 or below, only check for alarms
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SET_ALARM
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SET_ALARM), 1)
            }
        }
    }

}
@Composable
private fun TaskApp(navHostController: NavHostController){
    var darkThem by rememberSaveable {
        mutableStateOf(Constants.isThemDark)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val item = listOf(Screen.NotesScreen.route, Screen.TaskScreen.route,)
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
                            openDrawer = { scope.launch { drawerState.open() } },
                        )
                    },
                    bottomBar = {
                        BottomNavigation(
                            navHostController = navHostController,
                            isShow = showBottomBar,
                            backStackEntry=  backStackEntry
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


