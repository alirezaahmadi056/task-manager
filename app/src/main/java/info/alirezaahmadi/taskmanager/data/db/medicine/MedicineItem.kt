package info.alirezaahmadi.taskmanager.data.db.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.alirezaahmadi.taskmanager.R

@Entity
data class MedicineItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val image: String,
    val number: Int,
    val repetitionNumber: Int,
    val timeFrame: String = MedicineTimeFrame.HOURLY.name,
)

enum class MedicineTimeFrame(val perName: String) {
    HOURLY("ساعتی"),
    DAILY("روزانه"),
    MONTHLY("ماهانه")
}

data class MedicineNavItem(
    val name: String,
    val id: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
) {
    companion object {
        val medicineItems = listOf(
            MedicineNavItem(
                name = MedicineTimeFrame.HOURLY.perName,
                id = MedicineTimeFrame.HOURLY.name,
                selectedIcon = R.drawable.hourly,
                unSelectedIcon = R.drawable.hourly_outline
            ),
            MedicineNavItem(
                name = MedicineTimeFrame.DAILY.perName,
                id = MedicineTimeFrame.DAILY.name,
                selectedIcon = R.drawable.daily,
                unSelectedIcon = R.drawable.daily_outline
            ),
            MedicineNavItem(
                name = MedicineTimeFrame.MONTHLY.perName,
                id = MedicineTimeFrame.MONTHLY.name,
                selectedIcon = R.drawable.montly,
                unSelectedIcon = R.drawable.montly_outline
            ),

            )
    }
}
