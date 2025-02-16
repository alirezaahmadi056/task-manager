package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine.bottomNavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.data.model.SkinDayStatus
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@Composable
fun SkinBottomNavigation(
    currentPage: Int,
    onSelectedPage: (Int) -> Unit,
    allSkinRoutine: List<SkinRoutineItem>
) {
    val mavItemList = SkinDayStatus.statusList
    val badgeCounts = remember(allSkinRoutine) {
        mapOf(
            0 to allSkinRoutine.count { it.status == SkinStatus.DAY.name },
            1 to allSkinRoutine.count { it.status == SkinStatus.AFTERNOON.name },
            2 to allSkinRoutine.count { it.status == SkinStatus.NIGHT.name }
        )
    }

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
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        mavItemList.fastMapIndexed { index, skinDayStatus ->
            val badgeCount = badgeCounts[index] ?: 0
            SkinNavItem(
                item = skinDayStatus,
                isSelected = currentPage == index,
                badgeCount = badgeCount,
                onClick = { onSelectedPage(index) }
            )
        }
    }
}


@Composable
private fun SkinNavItem(
    item: SkinDayStatus,
    isSelected: Boolean,
    badgeCount: Int,
    onClick: () -> Unit
) {

    NavigationRailItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            BadgedBox(
                badge = {
                    AnimatedVisibility(
                        visible = !isSelected && badgeCount > 0,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Badge(
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                            contentColor = MaterialTheme.colorScheme.background,
                            containerColor = MaterialTheme.colorScheme.onBackground,
                        ) {
                            Text(
                                badgeCount.toString().byLocate(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(1.dp)
                            )
                        }
                    }


                }
            ) {
                Icon(
                    painter = if (isSelected) painterResource(item.selectedIcon) else
                        painterResource(item.unSelectedIcon),
                    contentDescription = "",
                )
            }

        },
        label = {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = NavigationRailItemDefaults.colors(
            selectedTextColor = MaterialTheme.colorScheme.onBackground,
            selectedIconColor =MaterialTheme.colorScheme.background,
            unselectedTextColor =MaterialTheme.colorScheme.onBackground.copy(0.8f),
            unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
            indicatorColor = MaterialTheme.colorScheme.onBackground
        )
    )
}