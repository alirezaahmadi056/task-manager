package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.bottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import info.alirezaahmadi.taskmanager.data.model.SkinDayStatus

@Composable
fun SkinBottomNavigation(
    currentPage: Int,
    onSelectedPage: (Int) -> Unit
) {

    val mavItemList = SkinDayStatus.statusList
    Row(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.5f
                )
            }
            .background(Color.White)
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        mavItemList.fastMapIndexed { index, skinDayStatus ->
            SkinNavItem(
                item = skinDayStatus,
                isSelected = currentPage == index,
                onClick = { onSelectedPage(index) }
            )
        }
    }
}

@Composable
private fun SkinNavItem(
    item: SkinDayStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    NavigationRailItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                painter = if (isSelected) painterResource(item.selectedIcon) else
                    painterResource(item.unSelectedIcon),
                contentDescription = "",
            )
        },
        label = {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors =  NavigationRailItemDefaults.colors(
            selectedTextColor = Color.Black,
            selectedIconColor = Color.White,
            unselectedTextColor = Color.DarkGray,
            unselectedIconColor = Color.DarkGray,
            indicatorColor = Color.Black
        )
    )
}