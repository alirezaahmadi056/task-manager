package ir.hoseinahmadi.taskmanager.data.db

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
     GREEN(Color(0xFF8BC34A)),
    ORANGE(Color(0xFFFF9800)),
    RED(Color(0xFFF44336)),
}
