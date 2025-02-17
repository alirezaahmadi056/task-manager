package ir.lrn.kara.data.db.goals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUri:String,
    val description: String,
    val timeFrame: String=GoalsTimeFrame.SHORT.name,
    val isCompleted: Boolean,
    val data: String
)


enum class GoalsTimeFrame(val perName: String) {
    SHORT("کوتاه مدت"),
    MEDIUM("میان مدت"),
    LONG("بلند مدت")
}