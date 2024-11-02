package info.alirezaahmadi.taskmanager

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import info.alirezaahmadi.taskmanager.navigation.NavGraph
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.AppConfig
import info.alirezaahmadi.taskmanager.ui.theme.TaskManagerTheme
import info.alirezaahmadi.taskmanager.util.Constants

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppConfig()
            navHostController = rememberNavController()
            TaskApp(navHostController)
            TskApp(navHostController)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TaskApp(navHostController: NavHostController) {

    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(notificationPermissionState.status.isGranted) {
        if (!notificationPermissionState.status.isGranted) {
            notificationPermissionState.launchPermissionRequest()
        }
    }

    /* requestPermissionNotification(
         notificationPermission = notificationPermissionState,
         isGranted = {
             Log.i("1212",it.toString())
         },
         permissionState = {
             it.launchPermissionRequest()
         },
     )*/
}

@Composable
private fun TskApp(navHostController: NavHostController) {

    var darkThem by rememberSaveable { mutableStateOf(Constants.isThemDark) }
    TaskManagerTheme(darkThem) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavGraph(
                modifier = Modifier
                    .fillMaxSize(),
                navHostController = navHostController,
                darkThem = { darkThem = it }
            )
        }
    }


}


@OptIn(ExperimentalPermissionsApi::class)
fun requestPermissionNotification(
    notificationPermission: PermissionState,
    isGranted: (Boolean) -> Unit,
    permissionState: (PermissionState) -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (notificationPermission.status.isGranted) {
            isGranted(true)
        } else {
            if (notificationPermission.status.shouldShowRationale) {
                isGranted(false)
            } else {
                permissionState(notificationPermission)
            }
        }
    } else {
        isGranted(true)
    }
}

@SuppressLint("InlinedApi")
fun requestExactAlarmPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        if (alarmManager?.canScheduleExactAlarms() == false) {
            Intent().also { intent ->
                intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                context.startActivity(intent)
            }
        }
    }
}
