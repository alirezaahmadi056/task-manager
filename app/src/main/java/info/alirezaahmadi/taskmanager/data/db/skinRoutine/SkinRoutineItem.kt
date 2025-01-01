package info.alirezaahmadi.taskmanager.data.db.skinRoutine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SkinRoutineItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val status: String = SkinStatus.DAY.name,
)

enum class SkinStatus {
    DAY, NIGHT
}
