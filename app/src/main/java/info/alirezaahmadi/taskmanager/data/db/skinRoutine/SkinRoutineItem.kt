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
    val color:Int,
    val image:Int,
    val time:String,
    val dayWeek :List<String>
)

enum class SkinStatus(val perName: String) {
    DAY("روز"),AFTERNOON("عصر"), NIGHT("شب")
}
