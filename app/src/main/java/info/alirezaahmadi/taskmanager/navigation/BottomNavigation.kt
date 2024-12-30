package info.alirezaahmadi.taskmanager.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LineStyle
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.icons.rounded.LineStyle
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private data class NavItem(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val text: String,
)

@Composable
fun BottomNavigation(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {

    val item = listOf(
        NavItem(
            selectedIcon = Icons.Rounded.NoteAlt,
            unSelectedIcon = Icons.Outlined.NoteAlt,
            text = "یادداشت",
        ),
        NavItem(
            selectedIcon = Icons.Rounded.Task,
            unSelectedIcon = Icons.Outlined.Task,
            text = "وظیفه",
        ),
        NavItem(
            selectedIcon = Icons.Rounded.LineStyle,
            unSelectedIcon = Icons.Outlined.LineStyle,
            text = "روتین",
        ),

        )
    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.4f)
        )
        NavigationBar (
            modifier = Modifier
                .fillMaxWidth(),
          containerColor = MaterialTheme.colorScheme.background
        ) {
            item.forEachIndexed { index, navItem ->
                val selected = pagerState.currentPage == index
                NavigationBarItem(selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index, animationSpec = tween(500))
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