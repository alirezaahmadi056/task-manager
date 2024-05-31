package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.navigation.Screen
import kotlin.math.roundToInt

@Composable
fun TaskScreen(navHostController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = false,
                text = {
                    Text(
                        text = "وظیقه",
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
                onClick = { navHostController.navigate(Screen.AddTaskScreen.route) })

        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val progress = 0.4f
            Box(contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(150.dp),
                    progress = { progress },
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.LightGray,
                    strokeCap = StrokeCap.Square,
                    strokeWidth = 4.dp
                )
                Text(text = (progress *100).roundToInt().toString())
            }



        }

    }


}


