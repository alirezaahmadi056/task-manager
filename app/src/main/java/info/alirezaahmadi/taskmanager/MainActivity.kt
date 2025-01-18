package info.alirezaahmadi.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import info.alirezaahmadi.taskmanager.navigation.NavGraph
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.AppConfig
import info.alirezaahmadi.taskmanager.ui.theme.TaskManagerTheme
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.SkinRoutineViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppConfig()
            navHostController = rememberNavController()
            LaunchedEffect(Unit) { handleNavigationIntent(navHostController, intent) }
            TskApp(navHostController)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNavigationIntent(navHostController, intent)
    }

    @Composable
    private fun TskApp(navHostController: NavHostController) {
        var darkThem by rememberSaveable { mutableStateOf(Constants.isThemDark) }
        TaskManagerTheme(isSystemInDarkTheme()) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                NavGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    navHostController = navHostController,
                    darkThem = { darkThem = it }
                )
            }
        }


    }


    private fun handleNavigationIntent(navHostController: NavHostController, intent: Intent) {
        if (intent.action == Constants.ACTION_TASK_RECEIVER) {
            intent.extras?.let {
                val id = it.getInt("TASK_ID", 0)
                navHostController.navigate(Screen.AddTaskScreen(id=id)) {
                    popUpTo(Screen.DutiesScreen) {
                        inclusive = false
                        saveState = true
                    }
                }
            }
        }

    }
}

