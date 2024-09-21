package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

private data class NavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val text: String,
)

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    isShow: Boolean,
    backStackEntry: State<NavBackStackEntry?>
) {
    if (isShow) {

        val item = listOf(
            NavItem(
                route = Screen.NotesScreen.route,
                selectedIcon = Icons.Rounded.NoteAlt,
                unSelectedIcon = Icons.Outlined.NoteAlt,
                text = "یادداشت",
            ),
            NavItem(
                route = Screen.TaskScreen.route,
                selectedIcon = Icons.Rounded.Task,
                unSelectedIcon = Icons.Outlined.Task,
                text = "وظیفه",
            ),

            )
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.4f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.forEachIndexed { index, navItem ->
                    val selected = navItem.route == backStackEntry.value?.destination?.route
                    NavigationBarItem(selected = selected,
                        onClick = {
                            if(!selected){
                                navHostController.navigate(navItem.route) {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selected) navItem.selectedIcon else navItem.unSelectedIcon,
                                contentDescription = ""
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = MaterialTheme.colorScheme.scrim.copy(0.7f),
                            selectedTextColor = MaterialTheme.colorScheme.scrim,
                            unselectedTextColor = MaterialTheme.colorScheme.scrim.copy(0.7f),
                            indicatorColor = MaterialTheme.colorScheme.primary
                        ),
                        label = {
                            Text(
                                text = navItem.text,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    )
                }


            }

        }


    }
}