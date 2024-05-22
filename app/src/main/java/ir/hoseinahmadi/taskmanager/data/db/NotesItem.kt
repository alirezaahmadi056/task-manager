package ir.hoseinahmadi.taskmanager.data.db

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String? = null,
    val startTime: String,
    val endTime: String,
    val taskColor: TaskColor
)

enum class TaskColor(val color: Color) {
     GREEN(Color(0xff089000)),
    ORANGE( Color(0xFFF69A0E)),
    RED(Color(0xFFF44336)),
}
