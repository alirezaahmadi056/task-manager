package ir.hoseinahmadi.taskmanager.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    isShow: Boolean
) {

    if (isShow) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var selected by rememberSaveable {
                mutableIntStateOf(0)
            }
            NavigationBarItem(selected = selected == 0,
                onClick = {
                    selected = 0
                    navHostController.navigate(Screen.NotesScreen.route)
                },
                icon = {
                    Icon(
                        Icons.Rounded.NoteAlt,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(text = "notes")
                }
            )

            NavigationBarItem(selected = selected == 1,
                onClick = {
                    selected = 1
                    navHostController.navigate(Screen.TaskScreen.route)
                },
                icon = {
                    Icon(
                        Icons.Rounded.Task,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(text = "task")
                }
            )


        }

    }
}