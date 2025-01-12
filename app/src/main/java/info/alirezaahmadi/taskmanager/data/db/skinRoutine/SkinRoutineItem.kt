package info.alirezaahmadi.taskmanager.data.db.skinRoutine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SkinRoutineItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val status: String = SkinStatus.DAY.perName,
)

enum class SkinStatus(val perName: String) {
    DAY("روز"),AFTERNOON("عصر"), NIGHT("شب")
}
