package info.alirezaahmadi.taskmanager.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val text: String,
)