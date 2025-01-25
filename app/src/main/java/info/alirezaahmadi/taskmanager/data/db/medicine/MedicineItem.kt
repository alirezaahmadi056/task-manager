package info.alirezaahmadi.taskmanager.data.db.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val image: String,
    val time: String,
    val dayWeek: List<String>,
)


