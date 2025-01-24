package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineNavItem
import info.alirezaahmadi.taskmanager.util.TaskHelper.byLocate

@Composable
fun MedicineBottomNavigation(
    currentTimeFrame: String,
    onSelectedTimeFrame:(String)->Unit
) {

    val mavItemList = remember { MedicineNavItem.medicineItems }
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
        mavItemList.forEach {  medicineItems ->
            // val badgeCount = badgeCounts[index] ?: 0
            SkinNavItem(
                item = medicineItems,
                isSelected = currentTimeFrame == medicineItems.id,
                badgeCount = 0,
                onClick = { onSelectedTimeFrame(medicineItems.id) }
            )
        }
    }

}

@Composable
private fun SkinNavItem(
    item: MedicineNavItem,
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
                            contentColor = Color.White,
                            containerColor = Color.Black,
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
                    modifier = Modifier.size(25.dp)
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
            selectedTextColor = Color.Black,
            selectedIconColor = Color.White,
            unselectedTextColor = Color.DarkGray,
            unselectedIconColor = Color.DarkGray,
            indicatorColor = Color.Black
        )
    )
}