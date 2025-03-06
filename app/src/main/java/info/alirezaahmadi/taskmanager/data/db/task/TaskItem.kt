package info.alirezaahmadi.taskmanager.data.db.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem(
    @PrimaryKey
    val id: Int = 0,
    val title: String = "",
    val subTask: List<Task> = emptyList(),
    val body: String = "",
    val taskColor: Int = 1,
    val createTime: String = "",
    val completedTime: String = "",
    val triggerAlarmTime: Long = 0L,
    val type: String = "NORMAL"
)

data class Task(
    var title: String="",
    var isCompleted :Boolean=false,
)
enum class TaskItemType{
    NORMAL,
    FAST
}

