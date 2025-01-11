package info.alirezaahmadi.taskmanager.data.model

import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus

data class SkinDayStatus(
    val name: String,
    val id: String,
    val selectedIcon: Int,
    val unSelectedIcon:Int,
) {
    companion object {
        val statusList = listOf(
            SkinDayStatus(
                id = SkinStatus.DAY.name,
                name = SkinStatus.DAY.perName,
                selectedIcon = R.drawable.sun,
                unSelectedIcon = R.drawable.sun_outline,
            ),
            SkinDayStatus(
                id = SkinStatus.AFTERNOON.name,
                name = SkinStatus.AFTERNOON.perName,
                selectedIcon = R.drawable.sunset,
                unSelectedIcon = R.drawable.sunset_outline,
                ),
            SkinDayStatus(
                id = SkinStatus.NIGHT.name,
                name = SkinStatus.NIGHT.perName,
                selectedIcon = R.drawable.moon,
                unSelectedIcon = R.drawable.moon_outline,
            )
        )
    }
}

